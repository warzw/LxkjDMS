#include "WTD5GParser.h"
#include <iostream>
#include <bitset>
#include <sstream>
#include <stdlib.h>
#include <map>
#include <algorithm>
#include <vector>
#include "../PublicFun.h"
#include "../PublicDefine.h"


namespace wtd
{

	WTD5GParser::WTD5GParser()
	{

	}

	WTD5GParser::~WTD5GParser()
	{

	}

	void WTD5GParser::Decode(WTD_ITEM1_TYPE type, const char * data, size_t length, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		Status s;
		bool ret = true;

		switch (type)
		{
		case WTD_ITEM1_DOOR:
			DecodeDoor(data, length, s, status);
			break;
		case WTD_ITEM1_REMIT:
			DecodeRemit(data, length, s, status);
			break;
		case WTD_ITEM1_SPEED:
			DecodeSpeed(data, length, s, status);
			break;
		case WTD_ITEM1_GROUP:
			DecodeGroup(data, length, s, status);
			break;
		case WTD_ITEM1_PANTO:
			DecodePanto(data, length, s, status);
			break;
		case WTD_ITEM1_VCB:
			DecodeVCB(data, length, s, status);
			break;
		case WTD_ITEM1_TOW_LEVEL:
			DecodeTowLevel(data, length, s, status);
			break;
		case WTD_ITEM1_BRAKE_LEVEL:
			DecodeBrakeLevel(data, length, s, status);
			break;
		case WTD_ITEM1_OCCUPY:
			DecodeDriverDesk(data, length, s, status);
			break;
		case WTD_ITEM1_SPLITPHASE:
			DecodeSplitPhase(data, length, s, status);
			break;
		case WTD_ITEM1_DDU:
			DecodeDDU(data, length, s, status);
			break;
		case WTD_ITEM1_CONSTANT_SPEED:
			DecodeConstSpd(data, length, s, status);
			break;
		case WTD_ITEM1_CONSTANTS_STATE:
			DecodeConstSpdState(data, length, s, status);
			break;
		case WTD_ITEM1_STANDBY_BRAKE:
			DecodeStdByBrake(data, length, s, status);
			break;
		case WTD_ITEM1_SIDEVOL_LINE:
			DecodeNetSideVol(data, length, s, status);
			break;
		case WTD_ITEM1_CURRENT_LINE:
			DecodeNetSideA(data, length, s, status);
			break;
		case WTD_ITEM1_PIPE_PRESSURE:
			DecodePipePress(data, length, s, status);
			break;
		case WTD_ITEM1_CYLINDER_PRESSURE:
			DecodeSylinderPress(data, length, s, status);
			break;
		case WTD_ITEM1_PULL_ELEC:
			DecodePullElec(data, length, s, status);
			break;
		case WTD_ITEM1_RENEWABLE_BRAKE:
			DecodeReBrake(data, length, s, status);
			break;
		case WTD_ITEM1_DIRECT_INSTRUCT:
			DecodeRunDir(data, length, s, status);
			break;
		case WTD_ITEM1_ALERT_STATE:
			DecodeAlertState(data, length, s, status);
			break;
		case WTD_ITEM1_UTC_TIME:
			DecodeUtcTime(data, length, s, status);
			break;
		default:
			ret = false;
			break;
		}

		if (ret)
		{
			status[type] = s;
		}
	}

	
	void WTD5GParser::DecodeEoas2(const unsigned char * data, size_t length, std::vector< TrainFault > & fault)
	{
		TrainFault _f;
		EOAS2_5G _p;
		memset( &_p, 0, sizeof(EOAS2_5G) );
		memcpy( &_p, data, sizeof(EOAS2_5G) );

		_f.time = _p.UTCTime;
		_f.trainsub = _p.TrainSub;
		_f.type = _p.FaultType;
		_f.code = _p.FaultCode;
		_f.desc = ((int)(_p.FaultStat) == 0) ? "发生" : "恢复";
		fault.push_back(_f);
	
	}

	//提取门的状态数据
	void WTD5GParser::DecodeDoor(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string state;
		int _doorStateType = WTD_DOOR_STATE_UNKNOWN;
		char _datas[16];
		memcpy(_datas, data,length);
		int uiCount = 0;
		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_GROUP);
		if (findIt == status.end())
		{
			return;
		}
		if(0x02 >= findIt->second.value && 0x00 < findIt->second.value)
			uiCount = 2 * findIt->second.value; // 唯一编组解析前8车，重联编组解析全部16车
		std::string strLeftDoorState;
		std::string strRightDoorState;

		TransferLogicalCabinToRealCabin(_datas, status);
		for(int i= 0; i < uiCount; ++i)
		{
			char num = _datas[i];
			for(int j = 2; j < 8; j += 2){//! because 1 and 8 cabin don't have the door
				char value =PublicFun::ExtractNumFromByte(num, j, 2);
				const std::string strcState = getDoorState(value);

				if(6 == j){//! because the real 4 cabin, in the left side
					strLeftDoorState += strcState + ";";
				}
				else{
					strRightDoorState += strcState + ";";
				}
			}

			++i;
			num = _datas[i];
			for(int j = 0; j < 6; j += 2){//! 6: because the 8 cabin doesn't have the door
				char value = PublicFun::ExtractNumFromByte(num, j, 2);
				const std::string strcState = getDoorState(value);

				if(0 == j){//! five cabin, in the right
					strRightDoorState += strcState + ";";
				}
				else{
					strLeftDoorState += strcState + ";";
				}
			}
		}

		TransferLogicalCabinToRealCabin(_datas + 8, status);
		for(int i = 0; i < uiCount; ++i){
			char num = _datas[8 + i];
			for(int j = 0; j < 8; j += 2){
				char value = PublicFun::ExtractNumFromByte(num, j, 2);
				const std::string strcState = getDoorState(value);

				if(6 == j)
				{//! 4 cabin the door in the left side
					strLeftDoorState += strcState + ";";
				}
				else{
					strRightDoorState += strcState + ";";
				}
			}

			++i;
			num = _datas[8 + i];
			for(int j = 2; j < 8; j += 2){//! 2: 5 cabin doesn't have the door
				char value = PublicFun::ExtractNumFromByte(num, j, 2);
				const std::string strcState = getDoorState(value);

				strLeftDoorState += strcState + ";";
			}
		}

		TransferLogicalCabinToRealCabin(_datas + 4, status);
		for(int i = 0; i < uiCount; ++i){//! the logical second door
			char num = _datas[4 + i];

			for(int j = 2; j < 8; j += 2){//! 2: 1 cabin doesn't have the door
				char value = PublicFun::ExtractNumFromByte(num, j, 2);
				const std::string strcState = getDoorState(value);

				if(6 == j){//! because the real 4 cabin, in the right side
					strRightDoorState += strcState + ";";
				}
				else{
					strLeftDoorState += strcState + ";";
				}
			}

			++i;
			num = _datas[4 + i];
			for(int j = 0; j < 6; j += 2){//! 6: because the 8 cabin doesn't have the door
				char value = PublicFun::ExtractNumFromByte(num, j, 2);
				const std::string strcState = getDoorState(value);

				if(0 == j){//! five cabin, in the left side
					strLeftDoorState += strcState + ";";
				}
				else{
					strRightDoorState += strcState + ";";
				}
			}
		}

		TransferLogicalCabinToRealCabin(_datas + 12, status);
		for(int i=0; i<uiCount; ++i){ //! the fourth door
			char num = _datas[12 + i];
			for(int j = 0; j < 8; j += 2){
				char value = PublicFun::ExtractNumFromByte(num, j, 2);
				const std::string strcState = getDoorState(value);

				if(6 == j){//! because the real 3 cabin, in the right side
					strRightDoorState += strcState + ";";
				}
				else{
					strLeftDoorState += strcState + ";";
				}
			}

			++i;
			num = _datas[12 + i];
			for(int j = 2; j < 8; j += 2){//! 2: because the 5 cabin doesn't have the door
				char value = PublicFun::ExtractNumFromByte(num, j, 2);
				const std::string strcState = getDoorState(value);

				strRightDoorState += strcState + ";";
			}
		}
		bool bLeftOpen = false;
		bool bRightOpen = false;

		std::map<WTD_ITEM1_TYPE,Status>::iterator findDIt = status.find(WTD_ITEM1_OCCUPY);
		if (findDIt == status.end())
		{
			return;
		}
		if(std::string::npos != strLeftDoorState.find("开")){
			if(findDIt->second.value == WTD_OCCUPY_TAIL){//! the value 0x13 sign 8 cabin is the occupied; 2: the occpuy location in the protocal
				bRightOpen = true;
			}
			else{
				bLeftOpen = true;
			}
		}

		if(std::string::npos != strRightDoorState.find("开")){
			if(findDIt->second.value == WTD_OCCUPY_TAIL){
				bLeftOpen = true;
			}
			else{
				bRightOpen = true;
			}
		}

		if(bLeftOpen && !bRightOpen)
		{
			state += "左门已打开";
			_doorStateType = WTD_DOOR_STATE_LEFT_OPEN;
		}
		else if(bLeftOpen && bRightOpen)
		{
			state += "两侧门已打开";
			_doorStateType = WTD_DOOR_STATE_ALL_OPEN;
		}
		else if(!bLeftOpen && bRightOpen)
		{
			state += "右门已打开";
			_doorStateType = WTD_DOOR_STATE_RIGHT_OPEN;
		}
		else if(!bLeftOpen && !bRightOpen)
		{
			state += "两侧门已关闭";
			_doorStateType = WTD_DOOR_STATE_ALL_CLOSE;
		}
		
	    s = PublicFun::InitProcData(WTD_ITEM1_DOOR,_doorStateType,state);		
	}

	//提取动车组速度
	void WTD5GParser::DecodeSpeed(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		int _speed = static_cast<unsigned char>(*data);
		s = PublicFun::InitProcData(WTD_ITEM1_SPEED,_speed * 1.1976, PublicFun::IntToStr(_speed * 1.1976));
	}

	//提取动车组编组
	void WTD5GParser::DecodeGroup(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		switch( int(*data) )
		{
		case 0x01:
			s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_SIGNAL,"否" );
			break;

		case 0x02:
			s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_DOUBLE,"是" );
			break;

		default:
			s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_UNKNOWN,"" );
			break;
		} 
	}

	//提取受电弓状态
	void WTD5GParser::DecodePanto(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//一个字节的数据
		std::ostringstream os;
		int _uiCount = 0;

		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_GROUP);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findDIt = status.find(WTD_ITEM1_OCCUPY);
		if (findIt == status.end() || findDIt == status.end())
		{
			return;
		}

		if(0x02 >= findIt->second.value && 0x00 < findIt->second.value)
			_uiCount = 2 * findIt->second.value; // 唯一编组解析前8车，重联编组解析全部16车

		//3,6,11,14车
		std::string states;
		for(int i=0, j=0; i<_uiCount; ++i, j+=2)
		{
			char _value = PublicFun::ExtractNumFromByte(*data,j,2);
			getPantoState(_value,os);
			os<< ";";
		}
		states = os.str();
		if(states.length() > 0)
		{
			//删除最后一个分号
			states.erase(states.end() - 1);
		}

		//转换为物理车厢顺序
		if(findIt->second.value == WTD_GROUP_SIGNAL) //单编组，无论1端还是8端占用，状态逻辑车厢v1-v8转换为物理v8-v1
		{
			//逻辑3-6车转换为物理3-6
			ReverseByDelimeter(states);
		}
		else
		{
			if(findDIt->second.value == WTD_OCCUPY_HEAD)//重联1车占用，状态逻辑车厢v8-v1+v16-v9转换为物理v1-v16
			{  //逻辑：3-6-11-14车=>物理：3-6-11-14
				std::vector<std::string> rect = PublicFun::split(states, ";");
				if (rect.size())
				{
					states = rect[1] + ";" + rect[0] + ";" + rect[3] + ";" + rect[2];
				}
				
			}
			else //重联8车占用，状态逻辑车厢v16-v1转换为物理v1-v16
			{
				ReverseByDelimeter(states);
			}
		}

		s = PublicFun::InitProcData(WTD_ITEM1_PANTO,0,states);
	}

	//提取主断路器状态
	void WTD5GParser::DecodeVCB(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::ostringstream os;
	    int uiCount = 0;
		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_GROUP);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findDIt = status.find(WTD_ITEM1_OCCUPY);
		if (findIt == status.end() || findDIt == status.end())
		{
			return;
		}

		if(0x02 >= findIt->second.value && 0x00 < findIt->second.value)
			uiCount = 1 * findIt->second.value; // 唯一编组解析前8车，重联编组解析全部16车

		const char * _p = data;

		std::string states;
		for(int i=0; i<uiCount; ++i)
		{
			char num = _p[i];
			for(int j = 0; j < 8; j += 4)
			{
				char _value = PublicFun::ExtractNumFromByte(num,j,2);
				getVCBState(_value,os);
				os << ";";
			}
		}
		states = os.str();
		if(states.length() > 0)
		{
			states.erase(states.end() - 1);
		}

		//单编组逻辑3-5-6车，双编组逻辑3-5-6-11-13-14
		if(findIt->second.value == WTD_GROUP_SIGNAL)
		{ //单编组，无论1端还是8端占用，状态逻辑车厢v1-v8转换为物理v8-v1
			ReverseByDelimeter(states); //逻辑：3-5-6=》物理：3-4-6
		}
		else
		{
			if(findDIt->second.value == WTD_OCCUPY_HEAD)//重联1车占用，状态逻辑车厢v8-v1+v16-v9转换为物理v1-v16
			{
				std::vector<std::string> rect = PublicFun::split(states, ";");
				if(rect.size() == 4)
				{
					states =  rect[1] + ";" + rect[0] + ";"+rect[3] + ";" + rect[2];
				}
				
			}
			else //重联8车占用，状态逻辑车厢v16-v1转换为物理v1-v16
			{
				ReverseByDelimeter(states);//逻辑3-5-6-11-13-14=》物理3-4-6-11-12-14
			}
		}

		s = PublicFun::InitProcData(WTD_ITEM1_VCB,0,states);
	}

	//提取动车组牵引级位
	void WTD5GParser::DecodeTowLevel(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		unsigned int uiPB = WTD_PB_UNKNOWN;
		std::ostringstream os;
		switch( int(_p[0]) )
		{
		case 0x00:
			os << "最小牵引位";	uiPB = WTD_PB_P0;
			break;

		case 0x08:
		case 0x02:
		case 0x04:
		case 0x01:
			os << "零位";	uiPB = WTD_PB_ZERO;
			break;

		case 0x10:
			os << "牵引位";	uiPB = WTD_PB_PULL;
			break;

		default:
			break;
		} // end switch

		switch( int(_p[1]) )		// 预留不解析
		{
		default:
			break;
		}

		s = PublicFun::InitProcData(WTD_ITEM1_TOW_LEVEL,uiPB,os.str());
	}

	//提取动车组制动级位
	void WTD5GParser::DecodeBrakeLevel(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		unsigned int uiPB = WTD_PB_UNKNOWN;
		std::ostringstream os;
		switch( int(_p[0]) )
		{
		case 0x01:
			os << "紧急制动位";	uiPB = WTD_PB_EB;
			break;

		case 0x02:
			os << "最大常用紧急制动位";	uiPB = WTD_PB_BL;
			break;

		case 0x04:
			os << "常用制动位";	uiPB = WTD_PB_BRAKE;
			break;

		case 0x08:
		case 0x00:
		case 0x10:
			os << "零位";	uiPB = WTD_PB_ZERO;
			break;

		default:
			break;
		} // end switch

		switch( int(_p[1]) )		// 预留不解析
		{
		default:
			break;
		}

		s = PublicFun::InitProcData(WTD_ITEM1_BRAKE_LEVEL,uiPB,os.str());
	}

	//提取停放制动状态
	void WTD5GParser::DecodeRemit(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		std::ostringstream os;
		int uiCount = 0;
		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_GROUP);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findDIt = status.find(WTD_ITEM1_OCCUPY);
		if (findIt == status.end() || findDIt == status.end())
		{
			return;
		}

		if(0x02 >= findIt->second.value && 0x00 < findIt->second.value)
			uiCount = 2 * findIt->second.value; // 唯一编组解析前8车，重联编组解析全部16车

		std::string states;
		for(int i=0; i<uiCount; ++i)
		{
			char num = _p[i];
			for(int j=0; j<8; j+=2)
			{
				char value = PublicFun::ExtractNumFromByte(num,j,2);
				getRemitState(value,os);
				os << ";";
				
			}
		}
		states = os.str();
		if(states.length() > 0)
		{
			states.erase(states.end()-1);
		}

		if(states.length() > 0)
		{
			if(findIt->second.value == WTD_GROUP_SIGNAL)
			{  //单编组时，逻辑车厢v1-v8解析成物理车厢v8-v1,仅物理车厢2,3,4,6,7车有效
				std::vector<std::string>vect = PublicFun::split(states,";");
				std::reverse(vect.begin(),vect.end());//现在是物理车厢1-8，需求去除1、5、8无效车厢
				if(8 == vect.size())
				{
					states = vect[1];//2车
					states += ";";
					states += vect[2];//3车
					states += ";";
					states += vect[3];//4车
					states += ";";
					states += vect[5];//6车
					states += ";";
					states += vect[6];//7车
				}
			}
			else
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD)
				{
					ConvertUnionEnd_1(states);
				}
				else
				{
					ConvertUnionEnd_8(states);
				}
				std::vector<std::string> vect = PublicFun::split(states,";");
				if(16 == vect.size())
				{
					states = vect[1];//2车
					states += ";";
					states += vect[2];//3车
					states += ";";
					states += vect[3];//4车
					states += ";";
					states += vect[5];//6车
					states += ";";
					states += vect[6];//7车
					states += ";";
					states += vect[9];//10车
					states += ";";
					states += vect[10];//11车
					states += ";";
					states += vect[11];//12车
					states += ";";
					states += vect[13];//14车
					states += ";";
					states = vect[14];//15车
				}
			}
		}
		s = PublicFun::InitProcData(WTD_ITEM1_REMIT,0,states);
	}

	//自动过分相装置断、合电信息GFX-3
	void WTD5GParser::DecodeSplitPhase(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		switch( int(*data))
		{
		case 0x00:
			s = PublicFun::InitProcData(WTD_ITEM1_SPLITPHASE,WTD_PHASE_NORMAL,"正常");
			break;

		case 0x01:
			s = PublicFun::InitProcData(WTD_ITEM1_SPLITPHASE,WTD_PHASE_IN,"进分相");
			break;

		case 0x02:
			s = PublicFun::InitProcData(WTD_ITEM1_SPLITPHASE,WTD_PHASE_OUT,"出分相");
			break;

		default:
			s = PublicFun::InitProcData(WTD_ITEM1_SPLITPHASE,WTD_PHASE_UNKNOWN,"");
			break;
		} // end switch
	}

	//司机台启用状态
	void WTD5GParser::DecodeDriverDesk(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		switch( int(*data) )
		{
		case 0x10:
			s = PublicFun::InitProcData(WTD_ITEM1_OCCUPY,WTD_OCCUPY_UNKNOWN,"未知");
			break;

		case 0x11:
			s = PublicFun::InitProcData(WTD_ITEM1_OCCUPY,WTD_OCCUPY_NO,"无占用");
			break;

		case 0x12:
			s = PublicFun::InitProcData(WTD_ITEM1_OCCUPY,WTD_OCCUPY_HEAD,"1车");
			break;

		case 0x13:
			s = PublicFun::InitProcData(WTD_ITEM1_OCCUPY,WTD_OCCUPY_TAIL,"8车");
			break;

		default:
			s = PublicFun::InitProcData(WTD_ITEM1_OCCUPY,WTD_OCCUPY_UNKNOWN,"未定义");
			break;
		} // end switch
	}

	//DDU所在物理车厢号
	void WTD5GParser::DecodeDDU(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		int _data = int(*data);
		switch( _data )
		{
		case 0x03:
			s = PublicFun::InitProcData(WTD_ITEM1_DDU,_data,"3车");
			break;

		case 0x06:
			s = PublicFun::InitProcData(WTD_ITEM1_DDU,_data,"6车");
			break;

		default:
			s = PublicFun::InitProcData(WTD_ITEM1_DDU,_data,"");
			break;
		} // end switch
	}

	//提取恒速速度
	void WTD5GParser::DecodeConstSpd(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		
		unsigned short _num = PublicFun::BitOrder(*((unsigned short*)(_p)));
		if(0xffff == _num)
		{
			return;
		}
		double _value = _num * 300.0/0xffff;
		s = PublicFun::InitProcData(WTD_ITEM1_CONSTANT_SPEED,_value,PublicFun::FormatDouble(_value,1));

	}
    
	//提取恒速状态
	void WTD5GParser::DecodeConstSpdState(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		// 恒速投入、切除状态
		if(0 == int(*_p))
		{
			s = PublicFun::InitProcData(WTD_ITEM1_CONSTANTS_STATE,WTD_CONSTANTSPEED_NO,"不恒速");
		}
		else if(1 == int(*_p))
		{
			s = PublicFun::InitProcData(WTD_ITEM1_CONSTANTS_STATE,WTD_CONSTANTSPEED_YES,"恒速");
		}
		else
		{
			s = PublicFun::InitProcData(WTD_ITEM1_CONSTANTS_STATE,WTD_CONSTANTSPEED_UNKNOWN,"");
		}
	}

	//提取备制投入状态
	void WTD5GParser::DecodeStdByBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		std::ostringstream os;
		int uiCount = 0;
		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_GROUP);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findDIt = status.find(WTD_ITEM1_OCCUPY);
		if (findIt == status.end() || findDIt == status.end())
		{
			return;
		}

		if(0x02 >= findIt->second.value && 0x00 < findIt->second.value)
			uiCount = 2 * findIt->second.value; // 唯一编组解析前8车，重联编组解析全部16车

		std::string states;
		for(int i=0; i<uiCount; ++i)
		{
			char num = _p[i];
			for(int j=0; j<8; j+=2)
			{
				char value = PublicFun::ExtractNumFromByte(num,j,2);
				getStdByBrake(value,os);
				os << ";";
			}
		}
		states = os.str();
		if(states.length() > 0)
		{
			states.erase(states.length()-1);
		}

		//转换为物理车厢顺序
		if(findIt->second.value == WTD_GROUP_SIGNAL)  //单编组，无论1端还是8端占用，状态逻辑车厢v1-v8转换为物理v8-v1
		{
			//逻辑1-8车转换为物理1-8
			ReverseByDelimeter(states);
		}
		else
		{
			if(findDIt->second.value == WTD_OCCUPY_HEAD)//重联1车占用，状态逻辑车厢v8-v1+v16-v9转换为物理v1-v16
			{  //逻辑：1-16车=>物理：1-16
				std::vector<std::string> rect = PublicFun::split(states, ";");

				//逻辑1-8=》物理1-8
				states = rect[7] + ";";
				states += rect[6] + ";";
				states += rect[5] + ";";
				states += rect[4] + ";";
				states += rect[3] + ";";
				states += rect[2] + ";";
				states += rect[1] + ";";
				states += rect[0] + ";";

				//逻辑9-16=》物理9-16
				states += rect[15] + ";";
				states += rect[14] + ";";
				states += rect[13] + ";";
				states += rect[12] + ";";
				states += rect[11] + ";";
				states += rect[10] + ";";
				states += rect[9] + ";";
				states += rect[8];
			}
			else//重联8车占用，状态逻辑车厢v16-v1转换为物理v1-v16
			{
				ReverseByDelimeter(states);
			}
		}

		s = PublicFun::InitProcData(WTD_ITEM1_STANDBY_BRAKE,0,states);
	}

	//解析网侧电压
	void WTD5GParser::DecodeNetSideVol(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string value = PublicFun::FormatDouble(static_cast<unsigned char>(*data) * 32.0 / 0xFF, 0);
		s = PublicFun::InitProcData(WTD_ITEM1_SIDEVOL_LINE,atoi(value.data()),value);
	}

	//解析网侧电流
	void WTD5GParser::DecodeNetSideA(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		
		std::string value = PublicFun::FormatDouble(static_cast<unsigned char>(*data) * 1000.0 / 0xFF, 0);
		s = PublicFun::InitProcData(WTD_ITEM1_CURRENT_LINE,atoi(value.data()),value);
	}

	//管压
	void WTD5GParser::DecodePipePress(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string _value;
		unsigned short num = PublicFun::BitOrder(*((unsigned short*)(data)));
		if(0xffff != num)
		{
			_value += PublicFun::FormatDouble(num * 100 * 10.0/10000, 1);
		}

		_value += ";";
		num = PublicFun::BitOrder(*((unsigned short*)(data+2)));
		if(0xffff != num)
		{
			_value += PublicFun::FormatDouble(num * 100 * 10.0/10000, 1);
		}
		s = PublicFun::InitProcData(WTD_ITEM1_PIPE_PRESSURE,0,_value);
	}

	//缸压
	void WTD5GParser::DecodeSylinderPress(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string _value;
		unsigned short num = PublicFun::BitOrder(*((unsigned short*)(data)));
		if(0xffff != num)
		{
			_value += PublicFun::FormatDouble(num * 100 * 10.0/10000, 1);
		}

		_value += ";";
		num = PublicFun::BitOrder(*((unsigned short*)(data+2)));
		if(0xffff != num)
		{
			_value += PublicFun::FormatDouble(num * 100 * 10.0/10000, 1);
		}
		s = PublicFun::InitProcData(WTD_ITEM1_CYLINDER_PRESSURE,0,_value);
	}

	//解析牵引电流
	void WTD5GParser::DecodePullElec(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findOIt = status.find(WTD_ITEM1_OCCUPY);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findDIt = status.find(WTD_ITEM1_DDU);
		if (findGIt == status.end() || findOIt == status.end() || findDIt == status.end())
		{
			return;
		}
		if(!(findDIt->second.desc == "3车" || findDIt->second.desc == "6车" ))
		{
			return;
		}

		double num1 = *(unsigned char*)(data);
		double num2 = *(unsigned char*)(data + 1);

		std::string value;
		std::string value1 = PublicFun::FormatDouble(num1 * 500.0/255,0);//1(8)车
		std::string value2 = PublicFun::FormatDouble(num2 * 500.0/255,0); //2(7)车

		if(findDIt->second.desc == "3车" )
		{
			double num3 = *(unsigned char*)(data + 2); //4车
			std::string value3 = PublicFun::FormatDouble(num3 * 500.0/255,0); //4车

			value += value1;
			value += ";";
			value += value2;
			value += ";";
			value += value3;

			if(findGIt->second.value == WTD_GROUP_SIGNAL)
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD)//数值v1-v8对应物理车厢v1-v8
				{
					; //无需转换
				}
				else//数值v1-v8对应物理车厢v8-v1
				{
					ReverseByDelimeter(value); //逻辑1-2-4对应物理8-7-5，反序后对应物理5-7-8
				}
			}
			else //重联
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD) //v1-v16转换成物理v1-v16
				{
					; //无需转换
				}
				else   //v16-v1转换成物理v1-v16
				{
					ReverseByDelimeter(value); //逻辑1-2-4对应物理16-15-13，反序后对应物理13-15-16
				}
			}
		}
		else
		{
			value += value2;//7车
			value += ";";
			value += value1;//8车

			if(findGIt->second.value == WTD_GROUP_SIGNAL)
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD)//数值v1-v8对应物理车厢v1-v8
				{
					;//无需转换
				}
				else //数值v1-v8对应物理车厢v8-v1
				{
					ReverseByDelimeter(value);//逻辑7-8对应物理8-7，反序后对应物理7-8
				}
			}
			else //重联
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD) //v1-v16转换成物理v1-v16
				{
					;//无需转换
				}
				else  //v16-v1转换成物理v1-v16
				{
					ReverseByDelimeter(value);
				}
			}
		}
		s = PublicFun::InitProcData(WTD_ITEM1_PULL_ELEC,0,value);
	}

	//解析再生制动力
	void WTD5GParser::DecodeReBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//传人6个字节的数据
		const char * _p = data;
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findOIt = status.find(WTD_ITEM1_OCCUPY);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findDIt = status.find(WTD_ITEM1_DDU);
		if (findGIt == status.end() || findOIt == status.end() || findDIt == status.end())
		{
			return;
		}
		if(!(findDIt->second.desc == "3车" || findDIt->second.desc == "6车" ))
		{
			return;
		}

		unsigned short num1 = PublicFun::BitOrder(*((unsigned short*)(_p)));
		unsigned short num2 = PublicFun::BitOrder(*((unsigned short*)(_p + 2)));
		unsigned short num3 = 0;
		if(findDIt->second.desc == "3车" )
		{
			num3 = PublicFun::BitOrder(*((unsigned short*)(_p + 4)));
		}

		std::string value;
		std::string value1;
		std::string value2;
		if(num1 < 32768)
		{
			value1 = PublicFun::FormatDouble(num1 * 400.0/32767, 2);
		}
		else
		{
			num1 -= 32768;
			value1 = PublicFun::FormatDouble(num1 * (-400.0)/32767, 2);
		}

		if(num2 < 32768)
		{
			value2 = PublicFun::FormatDouble(num2 * 400.0/32767, 2);
		}
		else
		{
			num2 -= 32768;
			value = PublicFun::FormatDouble(num2 * (-400.0)/32767, 2);
		}

		if(findDIt->second.desc == "3车" )//逻辑1-2-4车
		{
			std::string value3;
			unsigned short num3 = PublicFun::BitOrder(*((unsigned short*)(_p + 4)));
			if(num3 < 32768)
			{
				value3 = PublicFun::FormatDouble(num3 * 400.0/32767, 2);
			}
			else
			{
				num3 -= 32768;
				value3 = PublicFun::FormatDouble(num3 * (-400.0)/32767, 2);
			}

			value += value1;
			value += ";";
			value += value2;
			value += ";";
			value += value3;

			if(findGIt->second.value == WTD_GROUP_SIGNAL)
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD)//数值v1-v8对应物理车厢v1-v8
				{
					; //无需转换，逻辑1-2-4车对应物理1-2-4车
				}
				else//数值v1-v8对应物理车厢v8-v1
				{
					ReverseByDelimeter(value); //逻辑1-2-4对应物理8-7-5，反序后对应物理5-7-8
				}
			}
			else //重联
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD) //v1-v16转换成物理v1-v16
				{
					; //无需转换
				}
				else   //v16-v1转换成物理v1-v16
				{
					ReverseByDelimeter(value); //逻辑1-2-4对应物理16-15-13，反序后对应物理13-15-16
				}
			}
		}
		else//DDU在6车，只有逻辑8、7车有值
		{
			value += value2; //7车
			value += ";";
			value += value1;//8车

			if(findGIt->second.value == WTD_GROUP_SIGNAL)
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD)//数值v1-v8对应物理车厢v1-v8
				{
					;//
				}
				else //数值v1-v8对应物理车厢v8-v1
				{
					ReverseByDelimeter(value);//逻辑7-8对应物理8-7，反序后对应物理7-8
				}
			}
			else //重联
			{
				if(findDIt->second.value == WTD_OCCUPY_HEAD) //v1-v16转换成物理v1-v16
				{
					;//无需转换
				}
				else  //v16-v1转换成物理v1-v16
				{
					ReverseByDelimeter(value); //逻辑7-8刚好对应物理10-9,反序后9-10
				}
			}
		}

		s = PublicFun::InitProcData(WTD_ITEM1_RENEWABLE_BRAKE,0,value);
	}

	//解析方向手柄
	void WTD5GParser::DecodeRunDir(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string _value;
		char _num = *data;
		int _uiTemp = WTD_RUNDIR_UNKNOWN;
        switch(int(_num))
		{
		case 0:
			_value = "零位";
			_uiTemp = WTD_RUNDIR_ZERO;
			break;
		case 1:
			_value = "前进";
			_uiTemp = WTD_RUNDIR_FORWARD;
			break;
		case 2:
			_value = "后退";
			_uiTemp = WTD_RUNDIR_BACKWARD;
			break;
		default:
			break;
		}
		
		s = PublicFun::InitProcData(WTD_ITEM1_DIRECT_INSTRUCT,_uiTemp,_value);
	}

	//解析司机警惕装置状态
	void WTD5GParser::DecodeAlertState(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		std::ostringstream os;
		std::string states;
		for(int i=0; i<8; i+=2)
		{
			char value = PublicFun::ExtractNumFromByte(_p[0],i,2);
			 getAlertState(value,os);
			 os << ";";
		}
		states = os.str();
		s = PublicFun::InitProcData(WTD_ITEM1_ALERT_STATE,0,states);
	}

	//解析UTC时间
	void WTD5GParser::DecodeUtcTime(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		if (!data)
		{
			return;
		}
		unsigned int value = *(unsigned int*)data;
		value -= (8*60*60);
		s = PublicFun::InitProcData(WTD_ITEM1_UTC_TIME,value,PublicFun::LongToTime(value));
	}

	void WTD5GParser::getRemitState(const unsigned char state, std::ostringstream& oss, bool bflag/*=false*/)
	{
		if(!bflag)
		{
			switch( int(state))
			{
			case 0x01:	oss << "缓解";	break;
			case 0x02:	oss << "切除";	break;
			case 0x03:	oss << "施加";	break;
			default:					break;
			} // end switch
		}
		
	}

	void WTD5GParser::getPantoState(const unsigned char state, std::ostringstream& oss, bool bflag/*=false*/)
	{
		if(!bflag)
		{
			switch( int(state) )
			{
			case 0x00:	oss << "降";		break;
			case 0x01:	oss << "升";		break;
			case 0x02:	oss << "切除";	break;
			case 0x03:	oss << "故障";	break;
			default:					break;
			} // end switch
		}
	}

	void WTD5GParser::getVCBState(const unsigned char state, std::ostringstream& oss, bool bflag/*=false*/)
	{
		if(!bflag)
		{
			switch(int(state))
			{
			case 0x00:	oss << "断";		break;
			case 0x01:	oss << "合";		break;
			case 0x02:	oss << "切除";	break;
			case 0x03:	oss << "故障";	break;
			default:					break;
			} // end switch
		}
		
	}

	void WTD5GParser::getStdByBrake(const unsigned char state, std::ostringstream& oss, bool bflag/*=false*/)
	{
		switch(int(state))
		{
		case 0x01:
			oss<< "缓解";  break;
		case 0x02:
			oss<< "切除";  break;
		case 0x03:
			oss<< "制动";  break;
		default:
			oss<< "";      break;
		}
	}

	void WTD5GParser::getAlertState(const unsigned char state, std::ostringstream& oss, bool bflag/*=false*/)
	{
		switch(int(state))
		{
		case 0x00:
			oss << "未工作";
			break;
		case 0x01:
			oss << "工作";
			break;
		case 0x02:
			oss << "切除";
			break;
		case 0x03:
			oss << "故障";
			break;
		default:
			oss << "";
			break;
		}
	}

	const char* WTD5GParser::getDoorState(char num)
	{
		switch(num)
		{
		case 0:
			return "打开";
		case 1:
			return "关闭";
		case 2:
			return "切除";
		case 3:
			return "故障";
		default:
			return "";
		}
	}

	bool WTD5GParser::TransferLogicalCabinToRealCabin( char * pValue, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		bool bFlag = false;

		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findDIt = status.find(WTD_ITEM1_OCCUPY);
		if (findGIt == status.end() || findDIt == status.end())
		{
			return false;
		}

		if(1 == findGIt->second.value){//! single
			for(int i = 0; i < 4; ++i){
				PublicFun::SwapBitInByte(*pValue, 2 + i * 2, *(pValue + 1), 8 - i * 2, 2);
			}

			bFlag = true;
		}
		else if(2 == findGIt->second.value){//! reconnection
			if(findDIt->second.value == WTD_OCCUPY_HEAD){
				for(int i = 0; i < 4; ++i){
					PublicFun::SwapBitInByte(*pValue, 2 + i * 2, *(pValue + 1), 8 - i * 2, 2);
				}

				bFlag = true;
			}
			else if(findDIt->second.value == WTD_OCCUPY_TAIL){
				for(int i = 0; i < 4; ++i){
					PublicFun::SwapBitInByte(*pValue, 2 + i * 2, *(pValue + 3), 8 - i * 2, 2);
				}

				for(int i = 0; i < 4; ++i){
					PublicFun::SwapBitInByte(*(pValue + 1), 2 + i * 2, *(pValue + 2), 8 - i * 2, 2);
				}

				bFlag = true;
			}
		}

		return bFlag;
	}

	//逻辑车厢转化为物理车厢
	void WTD5GParser::ReverseByDelimeter(std::string& value)
	{
		if(value.empty())
		{
			return;
		}
		std::vector <std::string> _strList = PublicFun::split(value,";");
		
		int cnt = _strList.size();
		std::string _ret;
		for(int i=(cnt-1); i>=0; --i)
		{
			_ret += _strList[i];
			if(i > 0)
			{
				_ret += ";";
			}
		}
		value = _ret;
	}

	void WTD5GParser::ConvertUnionEnd_1(std::string & value)
	{
		//1端占用，逻辑v1-v8对应物理v8-v1，逻辑v9-v16对应物理v16-v9
		std::vector<std::string>vect = PublicFun::split(value,";");
		int size = vect.size();
		if(size != 16)
		{
			return;
		}
		std::string ret;
		ret += vect[7];//1车
		ret += ";";
		ret += vect[6];//2车
		ret += ";";
		ret += vect[5];//3车
		ret += ";";
		ret += vect[4];//4车
		ret += ";";
		ret += vect[3];//5车
		ret += ";";
		ret += vect[2];//6车
		ret += ";";
		ret += vect[1];//7车
		ret += ";";
		ret += vect[0];//8车
		ret += ";";

		ret += vect[15];//9车
		ret += ";";
		ret += vect[14];//10车
		ret += ";";
		ret += vect[13];//11车
		ret += ";";
		ret += vect[12];//12车
		ret += ";";
		ret += vect[11];//13车
		ret += ";";
		ret += vect[10];//14车
		ret += ";";
		ret += vect[9];//15车
		ret += ";";
		ret += vect[8];//16车
		value = ret;
	}

	void WTD5GParser::ConvertUnionEnd_8(std::string & value)
	{
		//8端占用，逻辑v16-v1对应物理v1-v16
		std::vector<std::string> vect = PublicFun::split(value,";");
		int size = vect.size();
		if(size != 16)
		{
			return;
		}
		std::string ret;
		ret += vect[15];//1车
		ret += ";";
		ret += vect[14];//2车
		ret += ";";
		ret += vect[13];//3车
		ret += ";";
		ret += vect[12];//4车
		ret += ";";
		ret += vect[11];//5车
		ret += ";";
		ret += vect[10];//6车
		ret += ";";
		ret += vect[9];//7车
		ret += ";";
		ret += vect[8];//8车
		ret += ";";

		ret += vect[7];//9车
		ret += ";";
		ret += vect[6];//10车
		ret += ";";
		ret += vect[5];//11车
		ret += ";";
		ret += vect[4];//12车
		ret += ";";
		ret += vect[3];//13车
		ret += ";";
		ret += vect[2];//14车
		ret += ";";
		ret += vect[1];//15车
		ret += ";";
		ret += vect[0];//16车
		value = ret;
	}
}
