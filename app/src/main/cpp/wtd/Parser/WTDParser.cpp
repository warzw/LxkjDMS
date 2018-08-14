#include "WTDParser.h"
#include <time.h>

namespace wtd
{
	std::map<std::string, GATEPAIR> WTDParser::m_GateMap;
	std::map<int, std::string> WTDParser::m_EmapCodeErr;
	std::map<int, std::string> WTDParser::m_380BLcodeDes;
	WTDParser::WTDParser():m_is16Emu(false)
	{
		m_GateMap["两侧车门关闭"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_ALL_CLOSE, "两侧门已关闭");
		m_GateMap["左门打开;右门打开"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_ALL_OPEN, "两侧门已打开");
		m_GateMap["左门打开;右门关闭"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_LEFT_OPEN, "左门已打开");
		m_GateMap["左门关闭;右门打开"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_RIGHT_OPEN, "右门已打开");
		m_GateMap["左门关闭;右门关闭"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_ALL_CLOSE, "两侧门已关闭");

		m_GateMap["至少一个门打开|右门禁用"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_LEFT_OPEN, "左门已打开");
		m_GateMap["至少一个门打开|左门禁用"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_RIGHT_OPEN, "右门已打开");
		m_GateMap["至少一个门打开|两侧门禁用"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_ALL_LOCKED, "两侧门锁定");
		m_GateMap["所有门关闭|两侧门禁用"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_ALL_CLOSE, "两侧门已关闭");
		m_GateMap["所有门关闭|右门禁用"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_LEFT_UNLOCKED, "左门释放");
		m_GateMap["所有门关闭|左门禁用"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_RIGHT_UNLOCKED, "右门释放");
		m_GateMap["所有门关闭"] = std::make_pair<unsigned int, std::string>(WTD_DOOR_STATE_ALL_CLOSE, "两侧门已关闭");
		m_GateMap["至少一个门打开"] = std::make_pair<unsigned int,std:: string>(WTD_DOOR_STATE_ONE_MORE_OPEN, "至少一个门打开");

		InitEmapCodeErr();
		Init380BLmapCodeErr();
	}

	WTDParser::~WTDParser()
	{

	}

	
	void WTDParser::setTrainData(int trainId,bool is380B)
	{
		m_is380B = is380B;
		m_trainId = trainId;
	}

	bool WTDParser::setWtd2Time(const unsigned char* pszData)
	{
		if(!(pszData[0] & 0x1)) return false;
		struct tm tm1 = { 0 };
		tm1.tm_year = ((pszData[1]>>4) & 0xf)*10 + (pszData[1] & 0xf) + 2000 -1900;
		tm1.tm_mon = ((pszData[2]>>4) & 0xf)*10 + (pszData[2] & 0xf) - 1;
		tm1.tm_mday = ((pszData[3]>>4) & 0xf)*10 + (pszData[3] & 0xf);
		tm1.tm_hour = ((pszData[4]>>4) & 0xf)*10 + (pszData[4] & 0xf);
		tm1.tm_min = ((pszData[5]>>4) & 0xf)*10 + (pszData[5] & 0xf);
		tm1.tm_sec = ((pszData[6]>>4) & 0xf)*10 + (pszData[6] & 0xf);
		tm1.tm_isdst = -1;
		m_WTD2Time = mktime(&tm1);
		return true;
	}

	void WTDParser::InitEmapCodeErr()
	{
		m_EmapCodeErr.clear();

		m_EmapCodeErr[2] = "牵引变流器 传输不良;";
		m_EmapCodeErr[4] = "牵引变流器 故障1;";
		m_EmapCodeErr[5] = "牵引变流器 故障2;";
		m_EmapCodeErr[52] = "制动控制装置 传输不良;";
		m_EmapCodeErr[59] = "制动控制装置 故障;";
		m_EmapCodeErr[60] = "制动控制装置 速度发电机断线1;";
		m_EmapCodeErr[61] = "制动控制装置 速度发电机断线2;";
		m_EmapCodeErr[62] = "制动控制装置 速度发电机断线3;";
		m_EmapCodeErr[63] = "制动控制装置 速度发电机断线4;";
		m_EmapCodeErr[143] = "辅助电源装置 通风机停止;";
		m_EmapCodeErr[135] = "辅助电源装置 故障;";
		m_EmapCodeErr[108] = "车门关闭故障（第1位）;";
		m_EmapCodeErr[109] = "车门关闭故障（第2位）;";
		m_EmapCodeErr[110] = "车门关闭故障（第3位）;";
		m_EmapCodeErr[111] = "车门关闭故障（第4位）;";
		m_EmapCodeErr[123] = "制动不足;";
		m_EmapCodeErr[134] = "牵引变流器 通风机停止;";
		m_EmapCodeErr[137] = "牵引电机 通风机1停止;";
		m_EmapCodeErr[138] = "牵引电机 通风机2停止;";
		m_EmapCodeErr[139] = "牵引变流器 微机故障;";
		m_EmapCodeErr[141] = "牵引变流器 故障;";
		m_EmapCodeErr[142] = "主电路接地;";
		m_EmapCodeErr[151] = "抱死1;";
		m_EmapCodeErr[152] = "抱死2;";
		m_EmapCodeErr[153] = "制动不缓解";;
		m_EmapCodeErr[154] = "轴温1;";
		m_EmapCodeErr[155] = "轴温2;";
		m_EmapCodeErr[162] = "主变压器 一次侧过电流;";
		m_EmapCodeErr[163] = "主变压器 三次侧过电流;";
		m_EmapCodeErr[164] = "主变压器 三次侧接地;";
		m_EmapCodeErr[165] = "主变压器 油泵停止;";
		m_EmapCodeErr[204] = "辅助电源装置 传输不良;";
		m_EmapCodeErr[166] = "辅助电源装置 VDTN跳闸;";

		//比380AL多出部分
		m_EmapCodeErr[657] = "距离传感器2 传感不良;";
		m_EmapCodeErr[661] = "距离传感器1 传感不良;";
		m_EmapCodeErr[665] = "距离传感器1 异常;";
		m_EmapCodeErr[666] = "距离传感器2 异常;";

		m_EmapCodeErr[695] = "车上检查开关“开”;";
		m_EmapCodeErr[696] = "车上检查开关“开”;";
		m_EmapCodeErr[826] = "编组间传输不良;";//多出部分

		m_EmapCodeErr[830] = "监控器传输不良中央1;";
		m_EmapCodeErr[832] = "监控器传输不良中央1;";
		m_EmapCodeErr[850] = "监控器传输不良中央1;";//多出部分
		m_EmapCodeErr[852] = "监控器传输不良中央1;";//多出部分

		m_EmapCodeErr[831] = "监控器传输不良中央2;";
		m_EmapCodeErr[833] = "监控器传输不良中央2;";
		m_EmapCodeErr[851] = "监控器传输不良中央2;";//多出部分
		m_EmapCodeErr[853] = "监控器传输不良中央2;";//多出部分

		for(int i=834; i<842; ++i)
		{
			m_EmapCodeErr[i] = "监控器传输不良终端;";
		}
		for(int i=854; i<862; ++i)
		{
			m_EmapCodeErr[i] = "监控器传输不良终端;";
		}
		m_EmapCodeErr[170] = "ACK1接通不良;";
		m_EmapCodeErr[194] = "受电弓上升位置异常;";
		m_EmapCodeErr[682] = "分相区信号处理装置 重故障;";
		m_EmapCodeErr[490] = "BIDS转向架异常1位转向架1轴;";//仅E27有，E28无
		m_EmapCodeErr[586] = "转向架异常;";                //仅E27有，E28无
		m_EmapCodeErr[491] = "BIDS转向架异常1位转向架2轴;";//仅E27有，E28无
		m_EmapCodeErr[492] = "BIDS转向架异常2位转向架1轴;";//仅E27有，E28无
		m_EmapCodeErr[493] = "BIDS转向架异常2位转向架2轴;";//仅E27有，E28无
		m_EmapCodeErr[156] = "牵引电机温度高;";            //仅E27有，E28无
		m_EmapCodeErr[80] = "空气压缩机油温高;";
		m_EmapCodeErr[82] = "PB异常动作;";
		m_EmapCodeErr[83] = "PB救援异常;";
		m_EmapCodeErr[265] = "轴温检测 温度报警;";
		m_EmapCodeErr[266] = "轴温检测 温度报警;";
	}

	void WTDParser::Init380BLmapCodeErr()
	{
		m_380BLcodeDes.clear();

		m_380BLcodeDes[0x680e] = "PIS柜火警";
		m_380BLcodeDes[0x6812] = "电气设备柜火警";
		m_380BLcodeDes[0x6816] = "盥洗室火警";
		m_380BLcodeDes[0x681A] = "牵引变流器火警";
		m_380BLcodeDes[0x681E] = "辅助变流器单元火警";
		m_380BLcodeDes[0x6820] = "司机室火警";
		m_380BLcodeDes[0x1739] = "乘客紧急制动被请求";
		m_380BLcodeDes[0x68C4] = "至少一个对轮对轴承测量温度故障（在发生故障TU的CCU和占用TU的HMI）";
		m_380BLcodeDes[0x68C7] = "列车自动停止热轴监控（在发生故障TU的CCU和占用TU的HMI）";
		m_380BLcodeDes[0x68C6] = "至少一个轴DNRA故障";
		m_380BLcodeDes[0x68C8] = "自动停止DNRA";
		m_380BLcodeDes[0x1736] = "至少一个WSP速度传感器信号故障";
		m_380BLcodeDes[0x19D1] = "1轴WSP检测到轴不旋转";
		m_380BLcodeDes[0x19D2] = "2轴WSP检测到轴不旋转";
		m_380BLcodeDes[0x19D3] = "3轴WSP检测到轴不旋转";
		m_380BLcodeDes[0x19D4] = "4轴WSP检测到轴不旋转";
		m_380BLcodeDes[0x19D5] = "1轴DNRA检测到轴不旋转";
		m_380BLcodeDes[0x19D6] = "2轴DNRA检测到轴不旋转";
		m_380BLcodeDes[0x19D7] = "3轴DNRA检测到轴不旋转";
		m_380BLcodeDes[0x19D8] = "4轴DNRA检测到轴不旋转";
		m_380BLcodeDes[0x2F81] = "1轴TCU检测到轴不旋转";
		m_380BLcodeDes[0x2F82] = "2轴TCU检测到轴不旋转";
		m_380BLcodeDes[0x2F83] = "3轴TCU检测到轴不旋转";
		m_380BLcodeDes[0x2F84] = "4轴TCU检测到轴不旋转";
		m_380BLcodeDes[0x68C5] = "至少有一个横向加速度传感器监控失效";
		m_380BLcodeDes[0x68C9] = "自动停止列车运行稳定性监控";
		m_380BLcodeDes[0x6B1A] = "若第一次发生横向加速度警报（173D或173E），最高速度将临时限制在280km/h达120秒";
		m_380BLcodeDes[0x6B1E] = "若在300公里内发生两次横向加速度警报，最高速度将一直被限制在280km/h";
		m_380BLcodeDes[0x6B22] = "如果最高速度持续被限制在280km/h后，300公里之内又发生2次新的横向加速度警报，那么最高速度将一直被限制在200km/h";
		m_380BLcodeDes[0x200A] = "TCU故障";
		m_380BLcodeDes[0x200B] = "TCU监控中止";
		m_380BLcodeDes[0x24CB] = "牵引电流变流器：内部风扇故障";
		m_380BLcodeDes[0x24CC] = "牵引箱风扇接触器：接通状态异常";
		m_380BLcodeDes[0x24D6] = "牵引电机风扇:接通状态异常";
		m_380BLcodeDes[0x24D7] = "驱动电机风扇:高级别故障";
		m_380BLcodeDes[0x25EF] = "中间直流环节接地故障监控起作用";
		m_380BLcodeDes[0x2504] = "牵引变流器冷却水泵故障";
		m_380BLcodeDes[0x2506] = "牵引箱风扇: MCS 关闭";
		m_380BLcodeDes[0x615A] = "自门控制器 1的MVB-PD-通信故障";
		m_380BLcodeDes[0x617D] = "自门控制器 4的MVB-PD-通信故障";
		m_380BLcodeDes[0x6A54] = "行车限制，车门未关闭";
		m_380BLcodeDes[0x6A55] = "关门并锁闭信号故障";
		m_380BLcodeDes[0x6A59] = "在CCU里'阻止门”的门控的'释放门'信号故障";
		m_380BLcodeDes[0x6CC1] = "EC: 司机-HMI1（左侧）的MVB-PD通讯故障";
		m_380BLcodeDes[0x6CC2] = "EC: 司机-HMI2（右侧）的MVB-PD通讯故障";
		m_380BLcodeDes[0x6CA3] = "变压器差动电流监控";
		m_380BLcodeDes[0x6CA8] = "车顶电缆：差动保护响应";
		m_380BLcodeDes[0x634B] = "10-T01: 变压器 - VCB关是由于变压器油流故障";
		m_380BLcodeDes[0x6CA5] = "VCB被切断：主变压器瓦斯报警";
		m_380BLcodeDes[0x63C1] = "10-X01: 升弓故障";
		m_380BLcodeDes[0x63C2] = "受电弓碳滑板断裂";
		m_380BLcodeDes[0x63C3] = "受电弓失效";
	}

}
