/*
 * wtd.h
 *
 *  Created on: Apr 28, 2016
 *      Author: wangyong
 */

#ifndef WTD_H_
#define WTD_H_

#include <string>
#include <string.h>

namespace wtd
{
enum WTD_DEV_TYPE
{
	WTD_DEV_UNKNOWN,
	WTD_DEV_380BL,
	WTD_DEV_380AL,
	WTD_DEV_ZE26, //株洲所
	WTD_DEV_ZE27, //株洲所
	WTD_DEV_5G,
	WTD_DEV_HE26, //华高
	WTD_DEV_HE27, //华高
};

enum WTD_ITEM1_TYPE
{
	WTD_ITEM1_UNKNOWN = 100,
	WTD_ITEM1_EOAS_TIME = 101,   //eoas打点时间
	WTD_ITEM1_WTD_TIME = 102,    //数据发送时的WTD系统时间
	WTD_ITEM1_TRAIN_NUM = 103,   //设备所在车号
	WTD_ITEM1_UTC_TIME = 104,    //UTC时间-19
	WTD_ITEM1_GROUP = 105,		// 编组方式(是否连挂)-4
	WTD_ITEM1_OCCUPY = 106,  // 占用端记录-5
	WTD_ITEM1_DOOR = 107,  // 车门状态-6
	WTD_ITEM1_SPEED = 108,  // 运行速度-7
	WTD_ITEM1_CONSTANT_SPEED = 109,  //恒速速度-8
	WTD_ITEM1_SPLITPHASE = 110, //自动过分相装置断合信息-9
	WTD_ITEM1_BIN = 111,  //BIN-10
	WTD_ITEM1_EB_SWITCH = 112, //紧急制动拉动开关状态-11
	WTD_ITEM1_BRAKE_INSTRUCT = 113, //制动指令-12
	WTD_ITEM1_DIRECT_INSTRUCT = 114, //方向指令-13
	WTD_ITEM1_HOST = 115,  //主控状态-14
	WTD_ITEM1_REMIT = 116,  //停放制动施加/缓解操作-15
	WTD_ITEM1_PHASE_SIGNAL = 117, //CCU给TCU的过分相信号-16
	WTD_ITEM1_VCB_SIGNAL = 118,  //ATP过分相分主断信号-17
	WTD_ITEM1_DDU = 119,   //DDU所在物理车厢号-18
	WTD_ITEM1_PANTO = 120,  //受电弓状态-3
	WTD_ITEM1_STORAGE_VOL = 121, //蓄电池电压-20
	WTD_ITEM1_PULL_ELEC = 122,  //牵引电流-21
	WTD_ITEM1_CONSTANTS_STATE = 123, //恒速状态-22
	WTD_ITEM1_STANDBY_BRAKE = 124, //备制制动投入-23
	WTD_ITEM1_CURRENT_LINE = 125, //网侧电流-24
	WTD_ITEM1_SIDEVOL_LINE = 126, //接触网电压-25
	WTD_ITEM1_CYLINDER_PRESSURE = 127, //缸压-26
	WTD_ITEM1_PIPE_PRESSURE = 128,     //管压-27
	WTD_ITEM1_RENEWABLE_BRAKE = 129,  //再生制动力-28
	WTD_ITEM1_AIRBRAKE_LEVEL = 130,  //空气制动率-29
	WTD_ITEM1_TOW_LEVEL = 131,  //牵引手柄级位-0
	WTD_ITEM1_BRAKE_LEVEL = 132, //制动手柄级位-1
	WTD_ITEM1_VCB = 133,   //主断路器状态-2
	WTD_ITEM1_HEADER_COVER = 134, //头罩状态
	WTD_ITEM1_VCB_OP = 135,         // 主断路器操作
	WTD_ITEM1_PANTO_OP = 136,       // 受电弓操作
	WTD_ITEM1_ALERT_STATE = 137,       //司机警惕装置状态
};

// 四方所5G(DDU) WTD EOAS1包数据条目子类型
enum WTD_ITEM1_5G_TYPE
{
	//WTD_ITEM1_5G_UNKNOWN,
	//WTD_ITEM1_5G_REMIT,
	//WTD_ITEM1_5G_PANTO,
    //WTD_ITEM1_5G_VCB,
	//WTD_ITEM1_5G_DOORNO1,		// 1位门状态
	//WTD_ITEM1_5G_DOORNO2,		// 2位门状态
	//WTD_ITEM1_5G_DOORNO3,		// 3位门状态
	//WTD_ITEM1_5G_DOORNO4,		// 4位门状态
};
// WTD BIN码翻译表
const char WTD_BIN_Describe[][64] =
{
	"合主断", "断主断", "降弓", "升弓", "开右门", "关右门", "开左门", "关左门",
	"紧急复位", "复位", "联挂", "解联", "", "", "", "",
	"罩开强制合", "罩关强制合", "司机室接地保护开关闭合", "关车门安全", "关门连锁", "CIR电源空开闭合", "救援转换装置电源空开闭合", "ATP电源空开闭合",
	"ATP输出B1", "ATP输出B4", "ATP输出牵引封锁", "LKJ电源空开闭合", "", "", "", ""
};

const char WTD_BIN_Describe_Open[][64] =
{
	"", "", "", "", "", "", "", "",
	"", "", "", "", "", "", "", "",
	"", "", "司机室接地保护开关断开", "", "", "CIR电源空开断开", "救援转换装置电源空开断开", "ATP电源空开断开",
	"", "", "", "LKJ电源空开断开", "", "", "", ""
};

// 牵引、制动手柄级位
enum WTD_PB_TYPE
{
	WTD_PB_UNKNOWN = 0,			// 未知
	WTD_PB_ZERO = 1,			// 零位
	WTD_PB_RELIEF = 2,			// 缓解
	WTD_PB_OC = 3,				// OC快速缓解
	WTD_PB_P0 = 4,				// 最小牵引位
	WTD_PB_B1A = 6,				// 制动1级A
	WTD_PB_B1B = 7,				// 制动1级B
	WTD_PB_BF = 8,				// 快速制动
	WTD_PB_BL = 9,				// 最大常用制动
	WTD_PB_BRAKE = 10,		    // 常用制动
	WTD_PB_B1,
	WTD_PB_B2,
	WTD_PB_B3,
	WTD_PB_B4,
	WTD_PB_B5,
	WTD_PB_B6,
	WTD_PB_B7,
	WTD_PB_B8,					// 制动8级
	WTD_PB_EB = 19,				// 紧急制动
	WTD_PB_PULL = 20,			// 牵引
	WTD_PB_P1,
	WTD_PB_P2,
	WTD_PB_P3,
	WTD_PB_P4,
	WTD_PB_P5,
	WTD_PB_P6,
	WTD_PB_P7,
	WTD_PB_P8,
	WTD_PB_P9,
	WTD_PB_P10,
};

// 运行方向（方向手柄）
enum WTD_RUNDIR_TYPE
{
	WTD_RUNDIR_UNKNOWN = 0,		// 未知
	WTD_RUNDIR_ZERO = 1,		// 零位
	WTD_RUNDIR_FORWARD = 2,		// 前进
 	WTD_RUNDIR_BACKWARD = 3,	// 后退
};

// 开关状态
enum WTD_SWITCH_TYPE
{
	WTD_SWITCH_UNKNOWN = 0,		// 未知
	WTD_SWITCH_ON = 1,			// 闭合
	WTD_SWITCH_OFF = 2,			// 断开
};

// 联挂状态（编组方式）
enum WTD_GROUP_TYPE
{
	WTD_GROUP_UNKNOWN = 0,			// 未知
	WTD_GROUP_SIGNAL = 1,		    // 非联挂（单编组）但非联挂有可能是16车厢如BL、AL等
	WTD_GROUP_DOUBLE = 2,		    // 联挂（重联编组）
};

// 占用端（司机台启用状态）
enum WTD_OCCUPY_TYPE
{
	WTD_OCCUPY_UNKNOWN = 0,			// 未知
	WTD_OCCUPY_HEAD = 1,		    // 占用端位于列车头部
	WTD_OCCUPY_TAIL = 2,		    // 占用端位于列车尾部
	WTD_OCCUPY_YES = 3,		    	// 行驶端为占用端
	WTD_OCCUPY_NO = 4,		    	// 行驶端为非占用端
};


// 受电弓状态
enum WTD_PANTO_TYPE
{
	WTD_PANTO_UNKNOWN = 0,			// 未知
	WTD_PANTO_FALL = WTD_PANTO_UNKNOWN,		    	// 降
	WTD_PANTO_RISE = 1,		   		// 升
	WTD_PANTO_ABLATE = 2, 			// 切除
	WTD_PANTO_FAULT = 3,		    // 故障
};

// 主断路器状态 保护开关 默认闭合
enum WTD_VCB_TYPE
{
	WTD_VCB_UNKNOWN = 0,		// 未知
	WTD_VCB_CLOSE = WTD_VCB_UNKNOWN,// 合
	WTD_VCB_OPEN = 1,		    // 断
	WTD_VCB_ABLATE = 2,		    // 切除
	WTD_VCB_FAULT = 3,		    // 故障
};

// 停放制动施加,缓解操作 PB作用
enum WTD_REMIT_TYPE
{
	WTD_REMIT_UNKNOWN = 0,			// 未知
	WTD_REMIT_RELIEF = 1,		    // 缓解
	WTD_REMIT_ABLATE = 2,		    // 切除
	WTD_REMIT_BRAKE = 3,		    // 制动
	WTD_REMIT_ISOLATE = WTD_REMIT_ABLATE,	// 隔离
};

// 过分相
enum WTD_PHASE_TYPE
{
	WTD_PHASE_UNKNOWN = 0,		// 未知
	WTD_PHASE_NORMAL = 1,		// 正常（复位）
	WTD_PHASE_CROSS = 2,		// 过分相(激活)
	WTD_PHASE_IN = WTD_PHASE_CROSS,		// 进分相
	WTD_PHASE_OUT = WTD_PHASE_NORMAL,	// 出分相
};

// 车门整体状态
enum WTD_DOOR_STATE
{
	WTD_DOOR_STATE_UNKNOWN = 0,		// 未知
	WTD_DOOR_STATE_ALL_OPEN = 1,	// 两侧门已打开
	WTD_DOOR_STATE_ALL_CLOSE = 2,	// 两侧门已关闭
	WTD_DOOR_STATE_ALL_LOCKED = 3,		// 两侧门锁定
	WTD_DOOR_STATE_LEFT_OPEN = 4,		// 左门已打开
	WTD_DOOR_STATE_LEFT_CLOSE = 5,		// 左门已关闭
	WTD_DOOR_STATE_LEFT_UNLOCKED = 6,			// 左门已释放
	WTD_DOOR_STATE_RIGHT_OPEN = 7,		// 右门已打开
	WTD_DOOR_STATE_RIGHT_CLOSE = 8,		// 右门已关闭
	WTD_DOOR_STATE_RIGHT_UNLOCKED = 9,			// 右门已释放
	WTD_DOOR_STATE_ONE_MORE_OPEN = 10,			// 至少一个门打开
};

// 车门状态
enum WTD_DOOR_TYPE
{
	WTD_DOOR_UNKNOWN = 0,		// 未知
	WTD_DOOR_OPEN = WTD_DOOR_UNKNOWN,		    // 打开
	WTD_DOOR_CLOSE = 1,		    // 关闭
	WTD_DOOR_ABLATE = 2,		// 切除
	WTD_DOOR_FAULT = 3,		    // 故障
};

// 普通状态
enum WTD_COMMON_TYPE
{
	WTD_COMMON_UNKNOWN = 0,		// 未知
	WTD_COMMON_STOP = WTD_COMMON_UNKNOWN,		// 未工作
	WTD_COMMON_RUN = 1,		    // 工作
	WTD_COMMON_ABLATE = 2,		// 切除
	WTD_COMMON_FAULT = 3,		// 故障
};

//恒速状态
enum WTD_CONSTANTSPEED_STATE
{
	WTD_CONSTANTSPEED_UNKNOWN,		// 未知
	WTD_CONSTANTSPEED_YES,		// 恒速
	WTD_CONSTANTSPEED_NO,		// 不恒速	
};
//备制投入
enum WTD_STANDBY_BRAKE
{
	WTD_STANDBY_BRAKE_UNKNOWN,		// 未知
	WTD_STANDBY_BRAKE_IN,		// 投入
	WTD_STANDBY_BRAKE_NORMAL,		// 正常	
};

//E26、E27的故障类型
enum WTD_FAULT_TYPE
{
	WTD_FAULT_TYPE_UNKNOW, 			// 不确定
	WTD_FAULT_TYPE_POWERSUPPLY, 	// 1 辅助供电系统
	WTD_FAULT_TYPE_TRACTIONDRIVE, 	// 2 牵引传动系统
	WTD_FAULT_TYPE_NETCONTROL, 		// 3 网络控制系统
	WTD_FAULT_TYPE_AIRSUPPLY, 		// 4 制动供风系统
	WTD_FAULT_TYPE_AIRCONDITION, 	// 5 空调通风系统
	WTD_FAULT_TYPE_TRAINDEVICE, 	// 6 车内设施
	WTD_FAULT_TYPE_VISITORINFO, 	// 7 旅客信息系统
	WTD_FAULT_TYPE_HIGHPOWERSUPPLY, // 8 高压供电系统
	WTD_FAULT_TYPE_MAX,
};

const unsigned int E27FaultLength = 256;
const unsigned int E26FaultLength = 269;

const unsigned int SYS_UNKNOW = 0x1;
const unsigned int SYS_POWERSUPPLY = 0x2;
const unsigned int SYS_TRACTIONDRIVE = 0x4;
const unsigned int SYS_NETCONTROL = 0x8;
const unsigned int SYS_AIRSUPPLY = 0x10;
const unsigned int SYS_AIRCONDITION = 0x20;
const unsigned int SYS_TRAINDEVICE = 0x40;
const unsigned int SYS_VISITORINFO = 0x80;
const unsigned int SYS_HIGHPOWERSUPPLY = 0x100;
//380BL的故障类型
enum WTD380BL_FAULT_TYPE
{
	WTD380_FAULT_UNKNOW = 0,
	WTD380_FAULT_HAPPEN,  //发生
	WTD380_FAULT_RECOVER, //恢复
	WTD380_FAULT_REPAIR,  //维修
};

class Status
{
public:
	unsigned int type; //WTD_ITEM1_TYPE
	unsigned int value; //WTD解析出来的字符对应的枚举
	std::string desc; //WTD解析出来的字符
};
class TrainFault
{
public:
	unsigned int trainsub; //故障所在车号
	unsigned int code;   //故障代码
	std::string desc; //故障描述
	unsigned int type; //故障类型
	unsigned int time;   //故障时间
};
}


#endif /* WTD_H_ */
