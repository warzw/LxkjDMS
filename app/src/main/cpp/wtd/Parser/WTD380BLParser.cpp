#include "WTD380BLParser.h"
#include <iostream>
#include <bitset>
#include <sstream>
#include <stdlib.h>
#include <map>

#include "../PublicFun.h"
#include <stdio.h>
#include "../PublicDefine.h"
#include "WTDFaultCode.h"


namespace wtd
{
//	WTD380BLParser::WTD380BLParser()
//	{
//
//	}
//
//	WTD380BLParser::~WTD380BLParser()
//	{
//
//	}

	void WTD380BLParser::Decode(WTD_ITEM1_TYPE type, const char * data, size_t length, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		Status s;
		bool ret = true;

		switch (type)
		{
		case WTD_ITEM1_UTC_TIME:
			DecodeUtcTime(data, length, s, status);
			break;
		case WTD_ITEM1_DOOR:
			DecodeDoor(data, length, s, status);
			break;
		case WTD_ITEM1_OCCUPY:
			DecodeOccupy(data, length, s, status);
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
		case WTD_ITEM1_HOST:
			DecodeHost(data, length, s, status);
			break;
		case WTD_ITEM1_SPLITPHASE:
			DecodeSplitPhase(data, length, s, status);
			break;
		case WTD_ITEM1_REMIT:
			DecodeRemit(data, length, s, status);
			break;
	/*	case WTD_ITEM1_PHASE_SIGNAL:
			DecodePhaseSignal(data, length, s,status);
			break;
		case WTD_ITEM1_VCB_SIGNAL:
			DecodeVCBSignal(data, length, s,status);
			break;*/
		/*case WTD_ITEM1_DIRECT_INSTRUCT:
			DecodeDirection(data, length, s,status);
			break;*/
		case WTD_ITEM1_CONSTANT_SPEED:
			DecodeConstSpd(data, length, s,status);
			break;
		case WTD_ITEM1_CONSTANTS_STATE:
			DecodeConstSpdState(data, length, s, status);
			break;
		case WTD_ITEM1_STANDBY_BRAKE:
			DecodeStdByBrake(data, length, s, status);
			break;
		case WTD_ITEM1_STORAGE_VOL:
			DecodeStorageVol(data, length, s,status);
			break;
		case WTD_ITEM1_PIPE_PRESSURE:
			DecodeParsePipe(data, length, s,status);
			break;
		case WTD_ITEM1_CYLINDER_PRESSURE:
			DecodeSylinder(data, length, s,status);
			break;
		case WTD_ITEM1_SIDEVOL_LINE:
			DecodeNetSideVol(data, length, s,status);
			break;
		case WTD_ITEM1_CURRENT_LINE:
			DecodeNetSideA(data, length, s,status);
			break;
		case WTD_ITEM1_PULL_ELEC:
			DecodePull(data, length, s,status);
			break;
		case WTD_ITEM1_RENEWABLE_BRAKE:
			DecodeReBrake(data, length, s,status);
			break;
		case WTD_ITEM1_AIRBRAKE_LEVEL:
			DecodeAirBrake(data, length, s,status);
			break;
		case WTD_ITEM1_DDU:
			DecodeDevNum(data, length, s,status);
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

	void WTD380BLParser::DecodeEoas2(const unsigned char * data, size_t length, std::vector< TrainFault > & fault)
	{
		TrainFault _f;
		InsertWtdItem2(_f, data, length);
		fault.push_back(_f);

	}

	void WTD380BLParser::InsertWtdItem2(TrainFault & fault, const unsigned char * data, size_t length)
	{
		if (length == sizeof(EOAS2_Sub380BL))
		{
			EOAS2_Sub380BL sub;
			memset( &sub, 0, sizeof(EOAS2_Sub380BL) );
			memcpy( &sub, data, sizeof(EOAS2_Sub380BL) );
			unsigned short wTrainNo = 0;
			unsigned char uCarNum = 0;
			if(getInfo_FromUIC(sub.iSegId, wTrainNo, uCarNum))
			{
				fault.trainsub = (int)uCarNum;
				fault.code = sub.iErrCode;
				fault.time = sub.iErrTime;
				fault.desc = m_380BLcodeDes[sub.iErrCode];
				char state = sub.iErrState;
				int bit0 = (int)state & 0x01;
				int bit1 = (int)state & 0x02;
				fault.type = WTD380_FAULT_UNKNOW;
				if( bit1 )
				{
					fault.type = WTD380_FAULT_REPAIR;
				}
				else
				{
					if( bit0 )
					{
						fault.type = WTD380_FAULT_HAPPEN;
					}
					else
					{
						fault.type = WTD380_FAULT_RECOVER;
					}
				}	
			}	
		}
	}

	bool WTD380BLParser::getInfo_FromUIC(unsigned int dwUIC , unsigned short& wTrainNo , unsigned char& uCarNum)
	{
		char szTemp[ 20 ] ;
		char szProject[ 20 ] ;
		char szTrainNo[ 20 ] ;
		char szCarNum[ 20 ] ;
		memset( szProject , 0x00 , sizeof( szProject ) ) ;
		memset( szTrainNo , 0x00 , sizeof( szTrainNo ) ) ;
		memset( szCarNum , 0x00 , sizeof( szCarNum ) ) ;
#ifdef EOAS_TQ
       _snprintf( szTemp , 20 , "%u" , dwUIC ) ;
#endif
#ifdef EOAS_RT
	   snprintf( szTemp , 20 , "%u" , dwUIC ) ;
#endif

		

		bool bValid = false ;
		////CRH380BL,eg:10401999，取最后3位
		if( strlen( szTemp ) == 8 + 1 )
		{
			memcpy( szProject , &szTemp[ 0 ] , 3 ) ;
			memcpy( szCarNum , &szTemp[ 3 ] , 2 ) ;
			memcpy( szTrainNo , &szTemp[ 5 ] , 3 ) ;
			bValid = true ;
		}
		////CRH3C,eg:104199，取最后2位
		if( strlen( szTemp ) == 6 + 1 )
		{
			memcpy( szProject , &szTemp[ 0 ] , 3 ) ;
			memcpy( szCarNum , &szTemp[ 3 ] , 1 ) ;
			memcpy( szTrainNo , &szTemp[ 4 ] , 2 ) ;
			bValid = true ;
		}

		//	unsigned long wProject = (unsigned long)strtoul( szProject , NULL , 10 ) ;
		wTrainNo = (unsigned short)strtoul( szTrainNo , NULL , 10 ) ;
		uCarNum = (unsigned char)strtoul( szCarNum , NULL , 10 ) ;

		return bValid ;
	}

	std::string WTD380BLParser::SetDoor(unsigned short state, unsigned int* puiDoor)
	{
		// 交换字节序
		unsigned short usState = state;
//		PublicFun::SwapUINT16( usState );

		unsigned short bit4 = usState & 0x0010; 
		unsigned short bit5 = usState & 0x0020;
		unsigned short bit7 = usState & 0x0080;
		unsigned short bit11 = usState & 0x0800;
		unsigned short bit12 = usState & 0x1000;


		std::ostringstream os;
		std::string openstate = "", blockstate = "";
		// 禁用状态
		if( bit11 != 0 && bit12 != 0 )
		{
			blockstate = "两侧门禁用";
		}
		else if( bit11 != 0 )
		{
			blockstate = "左门禁用";
		}
		else if( bit12 != 0 )
		{
			blockstate = "右门禁用";
		} // end if

		// 打开状态
		if( bit7 != 0 )
		{
			openstate = "至少一个门打开";
		}

		if( bit4 != 0 && bit5 != 0 )
		{
			openstate =  "所有门关闭";
		}

		if( !openstate.empty() && !blockstate.empty() )
		{
			os << openstate << "|" << blockstate;
		}
		else if(!openstate.empty())
		{
			os << openstate;
		}
		std::map<std::string,GATEPAIR>::iterator iter = m_GateMap.find(os.str());
		if(iter != m_GateMap.end())
		{
			os.str("");
			os << iter->second.second;
			*puiDoor = iter->second.first;
		}

		return os.str();
	}

	//提取utc时间
	void WTD380BLParser::DecodeUtcTime(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		unsigned int value = *(unsigned int*)data;
		//s = PublicFun::InitProcData( WTD_ITEM1_UTC_TIME,value,PublicFun::LongToTime(value));
	}

	void WTD380BLParser::DecodeDoor(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char *_p = data;
		
		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_GROUP);
		if (findIt == status.end())
		{
			return;
		}
		std::string str("");
		unsigned int uiDoor = WTD_DOOR_STATE_UNKNOWN;
		if(m_trainId % 2 == 1)
		{
			// 4节车厢一个单元，只根据第一个单元的车门状态判断，其他单元状态与第一单元相同
			str = SetDoor(*(unsigned short*)_p, &uiDoor);
		}
	   else
		{
			if(!m_is16Emu)
			{
				// 8节车厢时，3、4单元车门状态无效
				str = SetDoor(*(unsigned short*)(_p + 2), &uiDoor);
			}
			else if (m_is16Emu)
			{
				str = SetDoor(*(unsigned short*)(_p + 6), &uiDoor);
			}
			else
			{
				//便组未知
				str = SetDoor(*(unsigned short*)(_p + 6), &uiDoor);
				if(str.empty())
				{
					str = SetDoor(*(unsigned short*)(_p + 2), &uiDoor);
				}
			}
		}


		//s = PublicFun::InitProcData( WTD_ITEM1_DOOR,uiDoor,str);
	}

	//提取占用端记录
	void WTD380BLParser::DecodeOccupy(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		switch( int(*data) )
		{
		case 0x00:
			//s = PublicFun::InitProcData( WTD_ITEM1_OCCUPY,WTD_OCCUPY_NO,"不占用" );
			break;

		case 0x01:
			//s = PublicFun::InitProcData( WTD_ITEM1_OCCUPY,WTD_OCCUPY_YES,"占用" );
			break;

		default:
			s = PublicFun::InitProcData( WTD_ITEM1_OCCUPY,WTD_OCCUPY_UNKNOWN,"" );
			break;
		} // end switch
	}
	
	//提取动车组运行速度
	void WTD380BLParser::DecodeSpeed(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		unsigned short _speed = *(short*)(data);
		//s = PublicFun::InitProcData(WTD_ITEM1_SPEED,_speed, PublicFun::IntToStr(_speed));
	}

	//提取编组信息
	void WTD380BLParser::DecodeGroup(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		switch( int(*data) )
		{
		case 0x01:
			{
				s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_SIGNAL,"否" );
				if (m_is380B)
				{
					m_is16Emu = false;
				}
				else
				{
					m_is16Emu = true;
				}
				break;
			}

		case 0x02:
			{
				s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_DOUBLE,"是" );
				m_is16Emu = true;
				break;
			}

		default:
			s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_UNKNOWN,"" );
			break;
		}
	}

	//提取受电弓状态
	void WTD380BLParser::DecodePanto(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_HOST);
		if (findIt == status.end())
		{
			return;
		}
		// 仅主控端有效
		if( findIt->second.value != WTD_OCCUPY_YES )
		{
			s = PublicFun::InitProcData(WTD_ITEM1_PANTO,WTD_PANTO_UNKNOWN,"无效");
			return;
		}

		// 提取受电弓状态
		std::ostringstream os;
		unsigned int uiRet = 0, uiTmp;
		int i=0, j=0;
		for( ; i<4; ++i )
		{
			//os << i + 1 << "单元:";
			uiTmp = WTD_PANTO_UNKNOWN;
			if( int(_p[i]) == 0 )
			{
				os << "降";
				uiTmp = WTD_PANTO_FALL;
			}
			else if( int(_p[i]) == 1 )
			{
				os << "升";
				uiTmp = WTD_PANTO_RISE;
			}
			if( i != 3 ) os << ";";
			uiRet |= uiTmp << j*2;
			j+=4;	//四节车厢一个单元
		} // end for

		s = PublicFun::InitProcData(WTD_ITEM1_PANTO,uiRet,os.str());
	}

	//主断路器状态
	void WTD380BLParser::DecodeVCB(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		std::ostringstream os;
		unsigned int uiRet = 0, uiTmp;
		int i=0, j=0;
		for(; i<4; ++i )
		{
			//os << i + 1 << "单元:";
			uiTmp = WTD_VCB_UNKNOWN;
			if( int(_p[i]) == 0 )
			{
				os << "断";
				uiTmp = WTD_VCB_OPEN;
			}
			else
			{
				os << "合";
				uiTmp = WTD_VCB_CLOSE;
			}
			if( i != 3 ) os << ";";
			uiRet |= uiTmp << j*2;
			j+=4;	//四节车厢一个单元
		} // end if

		s = PublicFun::InitProcData(WTD_ITEM1_VCB,uiRet,os.str());
	}

	//提取牵引手柄级位
	void WTD380BLParser::DecodeTowLevel(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		short _towHandle =short(_p[1]<<8|_p[0]);
		//s = PublicFun::InitProcData(WTD_ITEM1_TOW_LEVEL,_towHandle,PublicFun::IntToStr(_towHandle));
		
	}
	//提取制动级位
	void WTD380BLParser::DecodeBrakeLevel(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string strResult;
		unsigned int uiPB = WTD_PB_UNKNOWN;
		switch( int(*data) )
		{
		case 0:					//Over Charge，更快的缓解
			strResult = "OC";	uiPB = WTD_PB_OC;
			break;
		case 1:
			strResult = "缓解";	uiPB = WTD_PB_RELIEF;
			break;
		case 2:
			strResult = "1A";	uiPB = WTD_PB_B1A;
			break;
		case 3:
			strResult = "1B";	uiPB = WTD_PB_B1B;
			break;
		case 4:
			strResult = "2级";	uiPB = WTD_PB_B2;
			break;
		case 5:
			strResult = "3级";	uiPB = WTD_PB_B3;
			break;
		case 6:
			strResult = "4级";	uiPB = WTD_PB_B4;
			break;
		case 7:
			strResult = "5级";	uiPB = WTD_PB_B5;
			break;
		case 8:
			strResult = "6级";	uiPB = WTD_PB_B6;
			break;
		case 9:
			strResult = "7级";	uiPB = WTD_PB_B7;
			break;
		case 10:
			strResult = "8级";	uiPB = WTD_PB_B8;
			break;
		case 11:
			strResult = "紧急";	uiPB = WTD_PB_EB;
			break;
		default:
			strResult = "";
		} // end switch

		s = PublicFun::InitProcData(WTD_ITEM1_BRAKE_LEVEL,uiPB,strResult);
	}

	//提取是否主控
	void WTD380BLParser::DecodeHost(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		// 占用端表示有主控钥匙插入，不管是1车还是8车。主控表示这一端插有主控钥匙
//		if( int(*data) == 0x01 )
//		{
//			s = PublicFun::InitProcData(WTD_ITEM1_HOST,WTD_HOST_YES,"是");
//		}
//		else
//		{
//			s = PublicFun::InitProcData(WTD_ITEM1_HOST,WTD_HOST_NO,"不是");
//		} // end if
	}

	//提取停放制动施加/缓解操作
	void WTD380BLParser::DecodeRemit(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		// 仅主控端有效
		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_HOST);
		if (findIt == status.end())
		{
			return;
		}
		// 仅主控端有效
		if( findIt->second.value != WTD_OCCUPY_YES )
		{
			s = PublicFun::InitProcData(WTD_ITEM1_REMIT,WTD_PANTO_UNKNOWN,"无效");
			return;
		}

		// 交换字节序
		unsigned short value = *(unsigned short *)data;
//		PublicFun::SwapUINT16(value);

		std::string strResult;
		unsigned int uiRemit = WTD_REMIT_UNKNOWN;
		if( (0x0020 & value) != 0x00 )
		{
			strResult = "隔离";
			uiRemit = WTD_REMIT_ISOLATE;
		}
		else
		{
			if( (0x0002 & value) != 0x00 )
			{
				strResult = "缓解";
				uiRemit = WTD_REMIT_RELIEF;
			}
			else
			{
				strResult = "施加";
				uiRemit = WTD_REMIT_BRAKE;
			} // end if
		}

		s = PublicFun::InitProcData(WTD_ITEM1_REMIT,uiRemit,strResult);
	}

	//提取自动过分相装置断合信息
	void WTD380BLParser::DecodeSplitPhase(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		if( int(*data) == 0x01 )
		{
			s = PublicFun::InitProcData(WTD_ITEM1_SPLITPHASE,WTD_PHASE_CROSS,"激活");
		}
		else if( int(*data) == 0x00 )
		{
			s = PublicFun::InitProcData(WTD_ITEM1_SPLITPHASE,WTD_PHASE_NORMAL,"正常");
		}
		else
		{
			s = PublicFun::InitProcData(WTD_ITEM1_SPLITPHASE,WTD_PHASE_UNKNOWN,"未知");
		}
	}

	//根据现在的协议得不到该数据，先屏蔽掉
	////提取CCU给TCU的过分相信号
	//void WTD380BLParser::DecodePhaseSignal(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	//{
	//	std::string ret = "";

	//	std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_HOST);
	//	if (findIt == status.end())
	//	{
	//		return;
	//	}
	//	if( findIt->second.value == WTD_OCCUPY_YES )		//仅主控端有效
	//	{
	//		if( int(*data) == 0x01)
	//			ret = "过分相开始";
	//	}
	//	else
	//		ret = "无效";

	//	s = PublicFun::InitProcData(WTD_ITEM1_PHASE_SIGNAL,*(int*)(data),ret);
	//}

	//// ATP过分相分主断信号
	//void WTD380BLParser::DecodeVCBSignal(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	//{
	//	std::string ret = "";

	//	std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_HOST);
	//	if (findIt == status.end())
	//	{
	//		return;
	//	}
	//	if( findIt->second.value == WTD_OCCUPY_YES )		//仅主控端有效
	//	{
	//		if( int(*data) == 1)
	//			ret = "分主断";
	//	}
	//	else
	//		ret = "无效";

	//	s = PublicFun::InitProcData(WTD_ITEM1_VCB_SIGNAL,*(int*)(data),ret);
	//}

	
	//提取恒速速度
	void WTD380BLParser::DecodeConstSpd(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		
		unsigned short _speed = *(short*)(data);
//		s = PublicFun::InitProcData(WTD_ITEM1_CONSTANT_SPEED,_speed, PublicFun::IntToStr(_speed));
		
	}

	//提取恒速状态
	void WTD380BLParser::DecodeConstSpdState(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
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

	//解析备制制动投入
	void WTD380BLParser::DecodeStdByBrake(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		//备用制动投入、恢复记录）
		if(0 == (int)*_p)
		{
			s = PublicFun::InitProcData(WTD_ITEM1_STANDBY_BRAKE,WTD_STANDBY_BRAKE_NORMAL,"正常");
		}
		else if(1 == (int)*_p)
		{
			s = PublicFun::InitProcData(WTD_ITEM1_STANDBY_BRAKE,WTD_STANDBY_BRAKE_IN,"投入");
		}
		else
		{
			s = PublicFun::InitProcData(WTD_ITEM1_STANDBY_BRAKE,WTD_STANDBY_BRAKE_UNKNOWN,"无效");
		}
	}

	 //解析接触网电压
	void WTD380BLParser::DecodeNetSideVol(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		std::string touchVol;
		// 仅主控端有效
		std::map<WTD_ITEM1_TYPE,Status>::iterator findHIt = status.find(WTD_ITEM1_HOST);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		if (findHIt == status.end() || findGIt == status.end())
		{
			return;
		}
		
		if(findHIt->second.value == WTD_OCCUPY_YES)
		{
			int cnt = 2;
			if(m_is16Emu)
			{
				cnt = 4;
			}
			for(int i = 0;i<cnt;i++)
			{
				if(i<(cnt-1))
				{
					touchVol += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
					touchVol += ";";
				}
				else
				{
					touchVol += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
				}
			}
		}
		
		s = PublicFun::InitProcData(WTD_ITEM1_SIDEVOL_LINE,0,touchVol);
	}

	//解析网侧电流
	void WTD380BLParser::DecodeNetSideA(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		std::string netCurrency;
		// 仅主控端有效
		std::map<WTD_ITEM1_TYPE,Status>::iterator findHIt = status.find(WTD_ITEM1_HOST);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		if (findHIt == status.end() || findGIt == status.end())
		{
			return;
		}
		if(findHIt->second.value == WTD_OCCUPY_YES)
		{
			int cnt = 2;
			if(m_is16Emu)
			{
				cnt = 4;
			}
			for(int i = 0;i<4;i++)
			{
				if(i<(cnt-1))
				{
					netCurrency += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
					netCurrency += ";";
				}
				else
				{
					netCurrency += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
				}
			}
		}
		s = PublicFun::InitProcData(WTD_ITEM1_CURRENT_LINE,0,netCurrency);
	}

	//解析蓄电池电压
	void WTD380BLParser::DecodeStorageVol(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//传8个字节的数据
		const char * _p = data;
		std::string storageVol;
		// 仅主控端有效
		std::map<WTD_ITEM1_TYPE,Status>::iterator findHIt = status.find(WTD_ITEM1_HOST);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		if (findHIt == status.end() || findGIt == status.end())
		{
			return;
		}
		if(findHIt->second.value == WTD_OCCUPY_YES)
		{
			int cnt = 2;
			if(m_is16Emu)
			{
				cnt = 4;
			}
			for(int i = 0;i<cnt;i++)
			{
				if(i<(cnt-1))
				{
//					storageVol += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
					storageVol += ";";
				}
				else
				{
//					storageVol += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
				}
			}
		}
		s = PublicFun::InitProcData(WTD_ITEM1_STORAGE_VOL,0,storageVol);
	}

	//解析缸压
	void WTD380BLParser::DecodeSylinder(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//传8个字节的数据
		const char * _p = data;
	   //	unsigned short _n = (unsigned short)_p;
		std::string sylinderPress;
		// 仅主控端有效
		std::map<WTD_ITEM1_TYPE,Status>::iterator findHIt = status.find(WTD_ITEM1_HOST);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		if (findHIt == status.end() || findGIt == status.end())
		{
			return;
		}
		if(findHIt->second.value == WTD_OCCUPY_YES)
		{
			int cnt = 2;
			if(m_is16Emu)
			{
				cnt = 4;
			}
			for(int i = 0;i<cnt;i++)
			{
				if(i<(cnt-1))
				{
					sylinderPress += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
					sylinderPress += ";";
				}
				else
				{
					sylinderPress += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
				}
			}
		}
		s = PublicFun::InitProcData(WTD_ITEM1_CYLINDER_PRESSURE,0,sylinderPress);
	}

	//解析管压
	void WTD380BLParser::DecodeParsePipe(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//传8个字节的数据
		const char * _p = data;
		//列车管空气压力（1---4单元）
		std::string pipePress;
		// 仅主控端有效
		std::map<WTD_ITEM1_TYPE,Status>::iterator findHIt = status.find(WTD_ITEM1_HOST);
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		if (findHIt == status.end() || findGIt == status.end())
		{
			return;
		}
		if(findHIt->second.value == WTD_OCCUPY_YES)
		{
			int cnt = 2;
			if(m_is16Emu)
			{
				cnt = 4;
			}
			for(int i = 0;i<cnt;i++)
			{
				if(i<(cnt-1))
				{
//					pipePress += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
					pipePress += ";";
				}
				else
				{
//					pipePress += PublicFun::LongToStr((unsigned long)(*(unsigned short*)(_p+(2*i))));
				}
			}
		}
		s = PublicFun::InitProcData(WTD_ITEM1_PIPE_PRESSURE,0,pipePress);
	}

	void WTD380BLParser::DecodeReBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string _brake;//再生制动力

		const char * _p = data;
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		if (findGIt == status.end())
		{
			return;
		}
		int cnt = 2;
		if(m_is16Emu)
		{
			cnt = 4;
		}
		for(int i=0; i<cnt; ++i)
		{
			short value = *(short*)(_p + 2*i);

			if(value >= 0)
			{
				_brake += "0";
			}
			else
			{
				_brake += PublicFun::IntToStr(-value);
			}
			if(i != (cnt-1))
			{
				_brake += ";";
			}
		}

		s = PublicFun::InitProcData(WTD_ITEM1_RENEWABLE_BRAKE,0,_brake);
	}

	//解析空气制动率
	void WTD380BLParser::DecodeAirBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		int _airBrake = *(unsigned short*)data;
		s = PublicFun::InitProcData(WTD_ITEM1_AIRBRAKE_LEVEL,_airBrake,PublicFun::IntToStr(_airBrake));
	}

	//提取方向手柄向前
	void WTD380BLParser::DecodeDirectionF(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string ret = "";
	    int _data = *(unsigned char *)data;
		unsigned int uiRet = WTD_RUNDIR_UNKNOWN;

		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_HOST);
		if (findIt == status.end())
		{
			return;
		}
		if( findIt->second.value == WTD_OCCUPY_YES )		//仅主控端有效
		{
			if( _data == 1)
			{
				ret = "前进";	
				uiRet = WTD_RUNDIR_FORWARD;
				s = PublicFun::InitProcData(WTD_ITEM1_DIRECT_INSTRUCT,uiRet,ret);
			}
		}

		
	}

	//提取方向手柄向后
	void WTD380BLParser::DecodeDirectionB(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string ret = "";
		int _data = *(unsigned char *)data;
		unsigned int uiRet = WTD_RUNDIR_UNKNOWN;

		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_HOST);
		std::map<WTD_ITEM1_TYPE,Status>::iterator DfindIt = status.find(WTD_ITEM1_DIRECT_INSTRUCT);
		if (findIt == status.end())
		{
			return;
		}
		if (DfindIt == status.end())
		{
			if( findIt->second.value == WTD_OCCUPY_YES )		//仅主控端有效
			{
				if( _data == 1)
				{
					ret = "后退";	
					uiRet = WTD_RUNDIR_BACKWARD;
					s = PublicFun::InitProcData(WTD_ITEM1_DIRECT_INSTRUCT,uiRet,ret);
				}
				else
				{
					ret = "零位";	
					uiRet = WTD_RUNDIR_ZERO;
					s = PublicFun::InitProcData(WTD_ITEM1_DIRECT_INSTRUCT,uiRet,ret);
				}
			}
		}
		
	}

	
	//提取设备所在车号
	void WTD380BLParser::DecodeDevNum(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		unsigned int _num = *(unsigned int *)data;
		s = PublicFun::InitProcData(WTD_ITEM1_DDU,_num,PublicFun::IntToStr(_num));

	}

	void WTD380BLParser::DecodePull(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string _pull; //牵引力

		const char * _p = data;
		std::map<WTD_ITEM1_TYPE,Status>::iterator findGIt = status.find(WTD_ITEM1_GROUP);
		if (findGIt == status.end())
		{
			return;
		}
		int cnt = 2;
		if(m_is16Emu)
		{
			cnt = 4;
		}
		for(int i=0; i<cnt; ++i)
		{
			short value = *(short*)(_p + 2*i);

			if(value >= 0)
			{
//				_pull += PublicFun::IntToStr(value);

			}
			else
			{
				_pull += "0";
			}
			if(i != (cnt-1))
			{
				_pull += ";";
			}
		}
		s = PublicFun::InitProcData(WTD_ITEM1_PULL_ELEC,0,_pull);
	}

}
