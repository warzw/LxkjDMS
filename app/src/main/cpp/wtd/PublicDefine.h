#ifndef PUBDEFINE_H
#define PUBDEFINE_H
namespace wtd
{
	#pragma pack(1)
	// 周期性数据包结构定义 54
	struct E26_EOAS1
	{
		char UtcTime[7];              //utc时间
		char IsGroup;				// 挂连状态(是否重连) 0
		char Occupy;				// 占用端记录  1
		short Speed;				// 运行速度 2-3
		char HandleBrake;			// 牵引/制动手柄级位 4
		char ConstSpdState;        // 恒速状态  5
		char Panto;				// 受电弓上升检测 6
		char StatVCB;				// 主断路器状态 7
		char StorageVol[5];        // 蓄电池电压 8-12
		char NetSideVol;           //网压  - 暂无数据13
		char NetSideA;             //网流  - 暂无数据14
		char Sylinder[9];          // 缸压 15-23
		char PullElec[7];          // 牵引电流 24- 30
		char ReBrake[6];           // 再生制动力 31 -36
		char DoorStat[4];			// 车门开信息 37-40
		char PB;                   // 停放制动 41
		char AlertBtn;            //警惕按钮 42
		char AlertIsolate;            //警惕隔离 43 
		char RescueAid;            //救援状态 44
		char RescueSignal;            //救援信号 45
		char Phase;				// 分项区间M613 46
		char BIN[4];				// BIN 47-50
		char EbpSwitch;			// 紧急制动拉动开关 51
		char BrakeInst;			// 制动指令 52
		char DirectInst;			// 方向指令 53	
	};

   // 周期性数据包结构定义 54
		struct E27_EOAS1
		{
			char UtcTime[7];              //utc时间
			char IsGroup;				// 挂连状态(是否重连) 7
			char Occupy;				// 占用端记录  8
			short Speed;				// 运行速度 9-10
			char HandleBrake;			// 牵引/制动手柄级位 11
			char ConstSpdState;        // 恒速状态  12
			char Panto;				// 受电弓上升检测 13
			char StatVCB;				// 主断路器状态 14
			char StorageVol[5];        // 蓄电池电压 15-19
			char NetSideVol;           //网压  - 暂无数据
			char NetSideA;             //网流  - 暂无数据
			char Sylinder[9];          // 缸压 22-30
			char PullElec[7];          // 牵引电流 31- 37
			char ReBrake[6];           // 再生制动力 38 -43
			char DoorStat[4];			// 车门开信息 44-47
			char PB;                 // 停放制动 48
			char AlertBtn;            //警惕按钮 49
			char AlertIsolate;            //警惕按钮 50 
			char RescueAid;            //救援状态 51
			char RescueSignal;            //救援信号 52
			char Phase;				// 分项区间M613 53
			char BIN[4];				// BIN 54-57
			char EbpSwitch;			// 紧急制动拉动开关 58
			char BrakeInst;			// 制动指令 59
			char DirectInst;			// 方向指令 60
	     };

         // 周期性数据包结构定义 80
		struct DDU_EOAS1
		{
			char DDU;                  //DDU所在物理车厢号 -0
			char IsGroup;				// 挂连状态(是否重连)-1
			char Occupy;				// 占用端记录-2
			char Speed;				// 运行速度 -3
			char HandleBrake[2];			// 牵引/制动手柄级位 4-5
			char ConstSpdState;        // 恒速状态 6
			short ConstSpeed;           // 恒速速度值 7-8
			char Remit[4];             // 停放制动状态 9-12
			char AlertDevState;        //警惕装置状态 13 
			char BowAct;               //受电弓操作 14
			char MainSWAct;            //主断路器操作 15
			char StdByBrake[4];        //备制投入状态 16-19
			char Panto;				    // 受电弓上升检测 20
			char StatVCB[2];			// 主断路器状态 21-22
			char NetSideVol;             //网压 23
			char NetSideA;               //网流 24
			char ChargerVol[16];          //充电机电压-目前没有解析 25-40
			char PipePress[4];          //管压 41-44
			char Sylinder[4];          // 缸压 45-48
			char PullElec[3];          // 牵引电流 49-51
			char ReBrake[6];           // 再生制动力 52-57
			char ALLDoorStat[4];			// 整体车门信息 -暂未用到58 -61
			char Phase;				    // 分项区间M613	-62
			char DoorStat[16];			// 车门开信息 63-78	
			char DirectInst;			// 方向指令 79
			char UTCTime[4];			// UTC时间 80-83

		};
       // 周期性数据包结构定义 85
		struct BL380_EOAS1
		{
			char UtcTime[4];              //utc时间
			char TrainNum[2];             //列号
			char DDU[4];                  //设备所在车号
			char IsGroup;				// 挂连状态(是否重连)————10
			char Occupy;				// 占用端记录
			char Host;                 //主控
			short Speed;				// 运行速度
			char HandleTow[2];		    //牵引手柄级位
			char BrakeRate[2];		    //空气制动率  17-18
			char ConstSpdState;        // 恒速状态 19
			short ConstSpeed;           // 恒速速度值 20-21
			char Remit[2];             // 停放制动状态 22-23
			char StdByBrake;        //备制投入状态
			char Panto[4];				    // 受电弓上升检测
			char StatVCB[4];				// 主断路器状态
			char NetSideVol[8];           //网压
			char NetSideA[8];             //网流
			char StorageVol[8];          //蓄电池电压
			char Sylinder[8];          // 缸压
			char PipePress[8];          //管压
			char PullElec[8];          // 牵引电流
			char DoorStat[8];			// 车门开信息
			char Phase;				    // 分项区间M613
			char HandleBrake;		    //制动手柄级位
			char PhaseSignal;           //CCU给TCU过分相信号
			char VCBSignal;             //VCB过分相主断信号
			char DirectInstF;			// 方向指令向前
			char DirectInstB;			// 方向指令向后
	
		};

		// 380BL报警数据包子包结构定义
		struct EOAS2_Sub380BL
		{
			unsigned int iErrTime;		// 故障时间
			unsigned int iSegId;		// UIC车号
			unsigned short iErrCode;	// 故障代码
			unsigned char iErrState;	// 故障状态 bit0: 1发生 0 恢复 bit1： 1维修模式
		};

		// 报警数据包结构定义
		struct EOAS2_5G
		{
			unsigned char TimeFlag;		// 时间有效位
			unsigned int  UTCTime;		// UTC时间
			unsigned char TrainSub;		// 报警车厢
			unsigned char FaultType;	// 报警系统
			unsigned short FaultCode;	// 故障代码
			unsigned char FaultStat;	// 故障状态 0 发生 1 恢复
			unsigned char Reserve[10];	//预留
		};

	#pragma pack()
}

#endif