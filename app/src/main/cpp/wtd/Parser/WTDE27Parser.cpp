#include "WTDE27Parser.h"
#include <iostream>
#include <bitset>
#include <sstream>
#include <stdlib.h>
#include <map>
#include "../PublicFun.h"
#include "WTDFaultCode.h"

namespace wtd
{
	WTDE27Parser::WTDE27Parser()
	{

	}

	WTDE27Parser::~WTDE27Parser()
	{

	}
	void WTDE27Parser::Decode(WTD_ITEM1_TYPE type, const char * data, size_t length, std::map<WTD_ITEM1_TYPE,Status> & status)
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
		case WTD_ITEM1_REMIT:
			DecodePB(data, length, s, status);
			break;
		case WTD_ITEM1_SPLITPHASE:
			DecodeSplitPhase(data, length, s, status);
			break;
		case WTD_ITEM1_BIN:
			DecodeBIN(data, length, s, status);
			break;
		case WTD_ITEM1_EB_SWITCH:
			DecodeEBSwitch(data, length, s, status);
			break;
		case WTD_ITEM1_BRAKE_INSTRUCT:
			DecodeBrakeInstruct(data, length, s, status);
			break;
		case WTD_ITEM1_DIRECT_INSTRUCT:
			DecodeDirectInstruct(data, length, s, status);
			break;
		case WTD_ITEM1_CONSTANTS_STATE:
			DecodeConstSpdState(data, length, s, status);
			break;
		case WTD_ITEM1_STORAGE_VOL:
			DecodeStorageVol(data, length, s, status);
			break;
		case WTD_ITEM1_CYLINDER_PRESSURE:
			DecodeSylinder(data, length, s, status);
			break;
		case WTD_ITEM1_PULL_ELEC:
			DecodePullElec(data, length, s, status);
			break;
		case WTD_ITEM1_RENEWABLE_BRAKE:
			DecodeReBrake(data, length, s, status);
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

	void WTDE27Parser::DecodeEoas2(const unsigned char * data, size_t length, std::vector< TrainFault > & fault)
	{

		unsigned int uiErrStat = 0;
		setWtd2Time(data);
		memcpy(&uiErrStat,data + 7,2);
		for(int i = 0;i<12;i++)
		{
			std::vector< TrainFault > _f;
			if(!((uiErrStat>>i) & 0x1)) continue; //判断各车故障是否有效

			switch(i)
			{
			case 0:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo01Center1,sizeof(m_E27uiNo01Center1)/sizeof(m_E27uiNo01Center1[0]));
				break;
			case 1:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo01Center2,sizeof(m_E27uiNo01Center2)/sizeof(m_E27uiNo01Center2[0]));
				break;
			case 2:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo08Center1,sizeof(m_E27uiNo08Center1)/sizeof(m_E27uiNo08Center1[0]));
				break;
			case 3:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo08Center2,sizeof(m_E27uiNo08Center2)/sizeof(m_E27uiNo08Center2[0]));
				break;
			case 4:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo01Terminal,sizeof(m_E27uiNo01Terminal)/sizeof(m_E27uiNo01Terminal[0]));
				break;
			case 5:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo02Terminal,sizeof(m_E27uiNo02Terminal)/sizeof(m_E27uiNo02Terminal[0]));
				break;
			case 6:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo03Terminal,sizeof(m_E27uiNo03Terminal)/sizeof(m_E27uiNo03Terminal[0]));
				break;
			case 7:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo04Terminal,sizeof(m_E27uiNo04Terminal)/sizeof(m_E27uiNo04Terminal[0]));
				break;
			case 8:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo05Terminal,sizeof(m_E27uiNo05Terminal)/sizeof(m_E27uiNo05Terminal[0]));
				break;
			case 9:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo06Terminal,sizeof(m_E27uiNo06Terminal)/sizeof(m_E27uiNo06Terminal[0]));
				break;
			case 10:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo07Terminal,sizeof(m_E27uiNo07Terminal)/sizeof(m_E27uiNo07Terminal[0]));
				break;
			case 11:
				InsertWtdItem2(_f,i,data,length,m_E27uiNo08Terminal,sizeof(m_E27uiNo08Terminal)/sizeof(m_E27uiNo08Terminal[0]));
				break;
			default:break;
			}
			for (int i = 0;i < (int)_f.size(); i++)
			{
				fault.push_back(_f[i]);
			}
		}

	}

	void WTDE27Parser::InsertWtdItem2(std::vector< TrainFault > & fault, unsigned int uiTrainDev, const unsigned char* pszData, const int iLen, const unsigned int uiStatMap[][3],const int iRowSize)
	{
		TrainFault _f;
		unsigned int uiFaultBit = 0;
		unsigned int uiFaultStat = 0;
		for(int i = 0; i<iRowSize; i++)
		{
			uiFaultBit = uiStatMap[i][0];
			if((uiFaultBit/8)> (unsigned int)iLen)
			{
				continue;
			}

			uiFaultStat = (pszData[uiFaultBit/8] >> (uiFaultBit%8)) & 0x1;//判断错误代码是否存在
			if(uiFaultStat)
			{
				for(int j = 0; j< WTD_FAULT_TYPE_MAX; j++)
				{
					if((uiStatMap[i][2]>>j) & 0x1)//判断故障归属
					{
						_f.trainsub = uiTrainDev;
						_f.type = j;
						_f.code = uiStatMap[i][1];
						_f.desc = m_EmapCodeErr[_f.code];
						_f.time = m_WTD2Time;
						fault.push_back(_f);
					}
				}
			}
		}
	}

	//提前BIN
	void WTDE27Parser::DecodeBIN(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		const char * _p = data;
		unsigned int uiBIN = 0;
		std::ostringstream os;
		if(0xff != (int)_p[0] || 0xff != (int)_p[1])
		{
			for( int i=0; i<=1; ++i )
			{
				std::bitset<8> bits = std::bitset<8>( _p[i] );
				for( int j=0; j<8; ++j )
				{
					std::string str = WTD_BIN_Describe[i*8+j];
					std::string strOpen = WTD_BIN_Describe_Open[i*8+j];
					if( bits.test(j) )
					{
						if( str.length() > 0 )
						{
							os << str << ";";
							uiBIN |= 0x01 << (i*8+j);
						}
					}
					else
					{
						if( strOpen.length() > 0 ) os << strOpen << ";";
					}
				} 
			} 
		}

		if(0xff != (int)_p[2] || 0xff != (int)_p[3])
		{
			for( int i=2; i<=3; ++i )
			{
				std::bitset<8> bits = std::bitset<8>( _p[i] );
				for( int j=0; j<8; ++j )
				{
					std::string str = WTD_BIN_Describe[i*8+j];
					std::string strOpen = WTD_BIN_Describe_Open[i*8+j];
					if( bits.test(j) )
					{
						if( str.length() > 0 )
						{
							os << str << ";";
							uiBIN |= 0x01 << (i*8+j);
						}
					}
					else
					{
						if( strOpen.length() > 0 ) os << strOpen << ";";
					}
				}
			} 
		}

		std::string strRet = os.str();
		PublicFun::eraseLastSymbol(strRet);
		s = PublicFun::InitProcData(WTD_ITEM1_BIN,uiBIN,strRet);
	}

	//提取制动指令
	void WTDE27Parser::DecodeBrakeInstruct(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::ostringstream os;
		std::bitset<8> bits = std::bitset<8>(*data);

		// 制动指令无效
		if( !bits.test(7) )
		{
			s = PublicFun::InitProcData(WTD_ITEM1_BRAKE_INSTRUCT,WTD_PB_UNKNOWN,"无效");
			return;
		}

		// 制动级别
		unsigned int uiCount=0;
		for( int i=0; i<7; ++i )
		{
			if( bits.test(i) )
			{
				uiCount++;
			}
		} 
		if(uiCount > 0)
		{
			os << uiCount << "级";
		}
		s = PublicFun::InitProcData(WTD_ITEM1_BRAKE_INSTRUCT,WTD_PB_BRAKE+uiCount,os.str());
	}

	//提取制动手柄级位
	void WTDE27Parser::DecodeBrakeLevel(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		// 0xFF表示无效
		if( int(*data) == 0xFF )
		{
			s = PublicFun::InitProcData(WTD_ITEM1_BRAKE_LEVEL,WTD_PB_UNKNOWN,"无效");
			return;
		}

		// 解析内容
		std::ostringstream os;
		std::bitset<8> bits = std::bitset<8>(*data);

		// 制动/牵引
		unsigned int uiPB = WTD_PB_UNKNOWN;
		if( bits.test(7) && !bits.test(6))
		{
			os << "制动" << int(int(*data) & 0x1F) << "级";
			uiPB = WTD_PB_BRAKE + int(int(*data) & 0x1F);
		}
		if( !bits.test(7) && bits.test(6))
		{
			os << "制动0级";
			uiPB = WTD_PB_ZERO;
		}
		if( !bits.test(7) && !bits.test(6))
		{
			if( bits.test(5))
			{
				os << "快速制动";
				uiPB = WTD_PB_BF;
			}
			else
			{
				os << "制动0级";
				uiPB = WTD_PB_ZERO;
			}
		}
		if( bits.test(7) && bits.test(6))
		{
			os << "制动0级";
			uiPB = WTD_PB_ZERO;
		}
		s = PublicFun::InitProcData(WTD_ITEM1_BRAKE_LEVEL,uiPB,os.str());
	}

	//提前方向指令
	void WTDE27Parser::DecodeDirectInstruct(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string strCmd = "";
		std::bitset<8> bits = std::bitset<8>(*data);

		// 方向指令无效
		if( !bits.test(7) )
		{
			s = PublicFun::InitProcData(WTD_ITEM1_DIRECT_INSTRUCT,WTD_RUNDIR_UNKNOWN,"无效");
			return;
		}

		// 4线
		if( bits.test(0) )	// 协议中前进、后退是相对1车来说的
		{
			if(int(*data) == 0x01)	strCmd =  "前进";
			else if(int(*data) == 0x02)	strCmd =  "后退";
		}
		// 5线
		else if( bits.test(1) )
		{
			if(int(*data) == 0x01)	strCmd =  "后退";
			else if(int(*data) == 0x02)	strCmd =  "前进";
		}
		else
			strCmd =  "零位";

		unsigned int uiRunDir = WTD_RUNDIR_UNKNOWN;
		if(strCmd == "零位")	uiRunDir = WTD_RUNDIR_ZERO;
		else if(strCmd == "前进")	uiRunDir = WTD_RUNDIR_FORWARD;
		else if(strCmd == "后退")	uiRunDir = WTD_RUNDIR_BACKWARD;

		s = PublicFun::InitProcData(WTD_ITEM1_DIRECT_INSTRUCT,uiRunDir,strCmd);
	}

	//提取恒速状态
	void WTDE27Parser::DecodeConstSpdState(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
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

	//解析蓄电池电压
	void WTDE27Parser::DecodeStorageVol(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//定义7个字节的数据
		std::string vols;
		const char * _p = data;
		char num = _p[0];
		int _1bit = int(num) & 0x02;//第1位，2车有效位
		int _2bit = int(num) & 0x04;//第2位，3车有效位
		int _4bit = int(num) & 0x10;//第4位，5车有效位
		int _6bit = int(num) & 0x40;//第6位，7车有效位
		if(_1bit != 0)
		{
			vols = PublicFun::IntToStr((int)_p[1]);
			vols += ";";
		}
		else
		{
			vols = ";";
		}

		if(_2bit != 0)
		{
			vols += PublicFun::IntToStr((int)_p[2]);
			vols += ";";
		}
		else
		{
			vols += ";";
		}

		if(_4bit != 0)
		{
			vols += PublicFun::IntToStr((int)_p[3]);
			vols += ";";
		}
		else
		{
			vols += ";";
		}

		if(_6bit != 0)
		{
			vols += PublicFun::IntToStr((int)_p[4]);
		}
		s = PublicFun::InitProcData(WTD_ITEM1_STORAGE_VOL,0,vols);
	}

	//解析缸压
	void WTDE27Parser::DecodeSylinder(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//传9个字节的数据
		std::string press;
		const char * _p = data;
		char num = _p[0];
		int _0bit = (int)num & 0x01;//第0位，1车有效位
		int _1bit = (int)num & 0x02;//第1位，2车有效位
		int _2bit = (int)num & 0x04;//第2位，3车有效位
		int _3bit = (int)num & 0x08;//第3位，4车有效位
		int _4bit = (int)num & 0x10;//第4位，5车有效位
		int _5bit = (int)num & 0x20;//第5位，6车有效位
		int _6bit = (int)num & 0x40;//第6位，7车有效位
		int _7bit = (int)num & 0x80;//第7位，8车有效位
		int bits[8] = {_0bit,_1bit,_2bit,_3bit,_4bit,_5bit,_6bit,_7bit};
		int pos = 1;
		for(int i=0; i<8; ++i, ++pos)
		{
			int flag = bits[i];
			if(flag > 0)
			{
				press += PublicFun::IntToStr(_p[pos]*4);
				press += ";";
			}
			else
			{
				press += ";";
			}
		}

		/*去除最后一个‘;’字符(不能下面这样使用，否则会报异常，
		因为这样不会改变press的长度，从而在使用string的+=操作符和c_str()函数后，
		可能会碰到意向不到的结果)*/
		//press[press.length()-1] = '\0';
		int len = press.length();
		if(len > 0)
		{
			press.resize(len-1);
		}
		s = PublicFun::InitProcData(WTD_ITEM1_CYLINDER_PRESSURE,0,press);
	}

	//解析牵引电流
	void WTDE27Parser::DecodePullElec(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//传7个字节的数据，第一位判断有效位
		const char *_p = data;
		std::string current;
		double ratio = 1040 / 255.0;
		char num = _p[0];
		int _1bit = int(num) & 0x02;//第1位，2号车有效位
		int _2bit = int(num) & 0x04;//第2位，3号车有效位
		int _3bit = int(num) & 0x08;//第3位，4车有效位
		int _4bit = int(num) & 0x10;//第4位，5车有效位
		int _5bit = int(num) & 0x20;//第5位，6号车有效位
		int _6bit = int(num) & 0x40;//第6位，7号车有效位

		if(_1bit > 0)
		{
			double value =double(_p[1]) * ratio;
			current += PublicFun::FormatDouble(value,1);
			current += ";";
		}
		else
		{
			current += ";";
		}
		if(_2bit > 0)
		{
			double value = double(_p[2]) * ratio;
			current += PublicFun::FormatDouble(value,1);
			current += ";";
		}
		else
		{
			current += ";";
		}
		if(_3bit > 0)
		{
			double value = double(_p[3]) * ratio;
			current += PublicFun::FormatDouble(value,1);
			current += ";";
		}
		else
		{
			current += ";";
		}
		if(_4bit > 0)
		{
			double value = double(_p[4]) * ratio;
			current += PublicFun::FormatDouble(value,1);
			current += ";";
		}
		else
		{
			current += ";";
		}
		if(_5bit > 0)
		{
			double value = double(_p[5]) * ratio;
			current += PublicFun::FormatDouble(value,1);
			current += ";";
		}
		else
		{
			current += ";";
		}
		if(_6bit > 0)
		{
			double value = double(_p[6]) * ratio;
			current += PublicFun::FormatDouble(value,1);
		}

		s = PublicFun::InitProcData(WTD_ITEM1_PULL_ELEC,0,current);
	}

	//解析再生制动力
	void WTDE27Parser::DecodeReBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		//传人6个字节的数据

		double num1 = *(unsigned char*)(data);
		double num2 = *(unsigned char*)(data + 1);
		double num3 = *(unsigned char*)(data + 2);
		double num4 = *(unsigned char*)(data + 3);
		double num5 = *(unsigned char*)(data + 4);
		double num6 = *(unsigned char*)(data + 5);
		
		std::string ton;
		double ratio = 6.67 / 255.0;

		double value = num1 * ratio;
		ton += PublicFun::FormatDouble(value,2);
		ton += ";";

		value = num2 * ratio;
		ton += PublicFun::FormatDouble(value,2);
		ton += ";";

		value = num3 * ratio; 
		ton += PublicFun::FormatDouble(value,2);
		ton += ";";

		value = num4 * ratio; 
		ton += PublicFun::FormatDouble(value,2);
		ton += ";";
		
		value = num5 * ratio;
		ton += PublicFun::FormatDouble(value,2);
		ton += ";";

		value = num6 * ratio;
		ton += PublicFun::FormatDouble(value,2);

		s = PublicFun::InitProcData(WTD_ITEM1_RENEWABLE_BRAKE,0,ton);
	}

	//解析UTC时间
	void WTDE27Parser::DecodeUtcTime(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::string utcTime;
		if (1 == ((*data) & 0x01))
		{
			std::string year,month,day,hour,min,sec;
			int iY = PublicFun::BCDTo_10(data[1]) + 2000;
			int iM = PublicFun::BCDTo_10(data[2]);
			int iD = PublicFun::BCDTo_10(data[3]);
			int iH = PublicFun::BCDTo_10(data[4]);
			int iMin = PublicFun::BCDTo_10(data[5]);
			int iS = PublicFun::BCDTo_10(data[6]);
			if(iY > 2050 || iY < 2012 ||
				iM > 12 || iM < 1 ||
				iD > 31 || iD < 1 ||
				iH > 24 || iH < 0 ||
				iMin > 59 || iMin < 0 ||
				iS > 59 || iS < 0)
			{
				utcTime = "无效时间";
				return;
			}
			year = PublicFun::LongToStr(iY);
			month = PublicFun::LongToStr(iM);
			day = PublicFun::LongToStr(iD);
			hour = PublicFun::LongToStr(iH);
			min = PublicFun::LongToStr(iMin);
			sec = PublicFun::LongToStr(iS);

			utcTime += year;
			utcTime += "-";
			utcTime += month;
			utcTime += "-";
			utcTime += day;
			utcTime += " ";
			utcTime += hour;
			utcTime += ":";
			utcTime += min;
			utcTime += ":";
			utcTime += sec;
		}
		else
		{
           int _a = 0;
		}
		s = PublicFun::InitProcData(WTD_ITEM1_UTC_TIME,0,utcTime);
		
	}

	//解析车门状态
	void WTDE27Parser::DecodeDoor(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		bool bSingleNumDoor = false;//奇数号门
		bool bDoubleNumDoor = false;//偶数号门

		std::map<WTD_ITEM1_TYPE,Status>::iterator findIt = status.find(WTD_ITEM1_OCCUPY);
		if (findIt == status.end())
		{
			return;
		}
		const char * _p = data;

		//解析车门信息
		std::ostringstream os;
		for( int i=0; i<4; ++i )
		{
			std::bitset<8> bits = std::bitset<8>( _p[i] );

			if(bits.test(0) || bits.test(2) || bits.test(4) || bits.test(6))
			{
				bSingleNumDoor = true;
			}
			else if(bits.test(1) || bits.test(3) || bits.test(5) || bits.test(7))
			{
				bDoubleNumDoor = true;
			}
			else
			{}
		}

		if(false == bSingleNumDoor && false == bDoubleNumDoor)
		{
			os << "两侧车门关闭";
		}
		else
		{
			if(0x01 == findIt->second.value)//面向1车
			{
				if(true == bDoubleNumDoor)
				{
					os << "左门打开;";
				}
				else
				{
					os << "左门关闭;";
				}

				if(true == bSingleNumDoor)
				{
					os << "右门打开";
				}
				else
				{
					os << "右门关闭";
				}

			}
			else if(0x02 == findIt->second.value)//面向8车
			{
				if(true == bSingleNumDoor)
				{
					os << "左门打开;";
				}
				else
				{
					os << "左门关闭;";
				}

				if(true == bDoubleNumDoor)
				{
					os << "右门打开";
				}
				else
				{
					os << "右门关闭";
				}
			}
			else
			{}
		}

		unsigned int uiDoor = WTD_DOOR_STATE_UNKNOWN;
		std::map<std::string,GATEPAIR>::iterator iter = m_GateMap.find(os.str());
		if(iter != m_GateMap.end())
		{
			os.str("");
			os << iter->second.second;
			uiDoor = iter->second.first;
		}
        s = PublicFun::InitProcData(WTD_ITEM1_DOOR,uiDoor,os.str());
	}

	//提取紧急制动拉动开关
	void WTDE27Parser::DecodeEBSwitch(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::ostringstream os;
		std::bitset<8> bits = std::bitset<8>( *data );

		// 1号车		// 5号车 	// 8号车
		unsigned int uiRet = 0, uiTmp;
		int i = 0;
		while( i < 8 )
		{
			uiTmp = WTD_SWITCH_UNKNOWN;
			if( bits.test(i) )
			{
				os << i+1 <<"车闭合;";
				uiTmp = WTD_SWITCH_ON;
			}
			uiRet |= uiTmp << i*2;
			if( i == 0)		i += 4;		else  	i += 3;
		}

		std::string strRet = os.str();
		PublicFun::eraseLastSymbol(strRet);
		s = PublicFun::InitProcData(WTD_ITEM1_EB_SWITCH,uiTmp,strRet);
	}

	//提取编组方式
	void WTDE27Parser::DecodeGroup(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		switch( int(*data) )
		{
		case 0x00:
			s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_SIGNAL,"否" );
			break;

		case 0x01:
			s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_DOUBLE,"是" );
			break;

		default:
			s = PublicFun::InitProcData( WTD_ITEM1_GROUP,WTD_GROUP_UNKNOWN,"" );
			break;
		}
	}

	//提取占用端记录
	void WTDE27Parser::DecodeOccupy(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		switch( int(*data) )
		{
		case 0x01:
			s = PublicFun::InitProcData( WTD_ITEM1_OCCUPY,WTD_OCCUPY_HEAD,"1车" );
			break;

		case 0x02:
			s = PublicFun::InitProcData( WTD_ITEM1_OCCUPY,WTD_OCCUPY_TAIL,"8车" );
			break;

		default:
			s = PublicFun::InitProcData( WTD_ITEM1_OCCUPY,WTD_OCCUPY_UNKNOWN,"" );
			break;
		}
	}

	//提取受电弓上升检测
	void WTDE27Parser::DecodePanto(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::ostringstream os;
		std::bitset<8> bits = std::bitset<8>( *data );

		// 4号车 	// 6号车
		unsigned int uiRet = 0, uiTmp;
		int i = 0, j = 3;
		for(; i < 2; i++)
		{
			uiTmp = WTD_PANTO_UNKNOWN;
			if( bits.test(i) )
			{
				os << "升";
				uiTmp = WTD_PANTO_RISE;
			}
			else
			{
				os << "降";
				uiTmp = WTD_PANTO_FALL;
			} // end if
			if( i != 1 ) os << ";";
			uiRet |= uiTmp << j*2;
			j += 2;
		}

		s = PublicFun::InitProcData(WTD_ITEM1_PANTO,uiRet,os.str());
	}

	//提取PB作用
	void WTDE27Parser::DecodePB(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::ostringstream os;
		std::bitset<8> bits = std::bitset<8>( *data );

		// 1号车	// 4号车	// 5号车	// 8号车
		unsigned int uiRet = 0, uiTmp;
		int i = 4, j = 0;
		for(; i < 8; i++)
		{
			uiTmp = WTD_REMIT_UNKNOWN;
			if( bits.test(i) )
			{
				os << "制动";
				uiTmp = WTD_REMIT_BRAKE;
			}
			else
			{
				os << "缓解";
				uiTmp = WTD_REMIT_RELIEF;
			} // end if
			if( i != 7 ) os << ";";
			uiRet |= uiTmp << j*2;
			if( j == 3) j++; else j += 3;
		}

		s = PublicFun::InitProcData(WTD_ITEM1_REMIT,uiRet,os.str());
	}

	//提取动车组运行速度
	void WTDE27Parser::DecodeSpeed(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		unsigned short _speed = *(short*)(data);
		s = PublicFun::InitProcData(WTD_ITEM1_SPEED,_speed, PublicFun::IntToStr(_speed));
	}

	//提取分项区间M613
	void WTDE27Parser::DecodeSplitPhase(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::ostringstream os;
		std::bitset<8> bits = std::bitset<8>( *data );

		// 4号车	// 6号车
		unsigned int uiRet = 0, uiTmp;
		int i = 4,j = 3;
		for(; i <= 6; i+=2)
		{
			uiTmp = WTD_PHASE_UNKNOWN;
			if( bits.test(i) )
			{
				os << "过分相";
				uiTmp = WTD_PHASE_CROSS;
			}
			else
			{
				os << "复位";
				uiTmp = WTD_PHASE_NORMAL;
			} // end if
			if( i != 6 ) os << ";";
			uiRet |= uiTmp << j*2;
			j += 2;
		}

		s = PublicFun::InitProcData(WTD_ITEM1_SPLITPHASE,uiRet,os.str());
	}

	//提前牵引级位
	void WTDE27Parser::DecodeTowLevel(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		// 0xFF表示无效
		if( int(*data) == 0xFF )
		{
			s = PublicFun::InitProcData(WTD_ITEM1_TOW_LEVEL,WTD_PB_UNKNOWN,"无效");
			return;
		}

		// 解析内容
		std::ostringstream os;
		std::bitset<8> bits = std::bitset<8>(*data);
        // 牵引
		unsigned int uiPB = WTD_PB_UNKNOWN;
		
		if( bits.test(6) &&  !bits.test(7) )
		{
			os << "牵引" << int(int(*data) & 0x1F) << "级";
			uiPB = WTD_PB_PULL + int(int(*data) & 0x1F);
		}
		else if( !bits.test(6) )
		{
			os << "牵引0级";
			uiPB = WTD_PB_ZERO;
		}
		if( bits.test(7) && bits.test(6))
		{
			os << "牵引0级";
			uiPB = WTD_PB_ZERO;
		}
		s = PublicFun::InitProcData(WTD_ITEM1_TOW_LEVEL,uiPB,os.str());
	}

	//提取主断路器状态
	void WTDE27Parser::DecodeVCB(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status)
	{
		std::ostringstream os;
		std::bitset<8> bits = std::bitset<8>( *data );

		// 2号车	// 4号车	// 6号车
		unsigned int uiRet = 0, uiTmp;
		int i=0, j=1;
		for( ; i<=2; i++ )
		{
			//os << (i+1)*2 << "号车:";
			uiTmp = WTD_VCB_UNKNOWN;
			if( bits.test(i) )
			{
				os << "断";
				uiTmp = WTD_VCB_OPEN;
			}
			else
			{
				os << "合";
				uiTmp = WTD_VCB_CLOSE;
			}
			if( i != 2 ) os << ";";
			uiRet |= uiTmp << j*2;
			j += 2;
		} // end for

		s = PublicFun::InitProcData(WTD_ITEM1_VCB,uiRet,os.str());
	}

};
