/*
 * WTD380BLParser.h
 *
 *  Created on: Apr 28, 2016
 *      Author: wangyong
 */

#ifndef WTD380BLPARSER_H_
#define WTD380BLPARSER_H_

#include "WTDParser.h"

namespace wtd
{
class WTD380BLParser : public WTDParser
{
public:
	WTD380BLParser();
	virtual ~WTD380BLParser();

	virtual void Decode(WTD_ITEM1_TYPE type, const char * data, size_t length, std::map<WTD_ITEM1_TYPE,Status> & status); //解析一包数据

	virtual void DecodeEoas2(const unsigned char * data, size_t length, std::vector< TrainFault > & fault); //解析二包数据

private:
	std::string SetDoor(unsigned short state, unsigned int* puiDoor);

	void DecodeUtcTime(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取Utc时间
	void DecodeDoor(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取门状态信息
	void DecodeOccupy(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取占用端记录
	void DecodeSpeed(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取动车组运行速度
	void DecodeGroup(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取编组信息
	void DecodePanto(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //提取受电弓状态
	void DecodeVCB(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);  //主断路器状态
	void DecodeTowLevel(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取牵引手柄级位
	void DecodeBrakeLevel(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取制动级位
	void DecodeHost(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取是否主控
	void DecodeRemit(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);//提取停放制动施加/缓解操作
	void DecodeSplitPhase(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);//提取自动过分相装置断合信息
	//void DecodePhaseSignal(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);		// CCU给TCU的过分相信号
	//void DecodeVCBSignal(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);		
	void DecodeConstSpd(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //提取恒速速度
	void DecodeConstSpdState(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取恒速状态
	void DecodeStdByBrake(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //解析备制制动投入
	void DecodeNetSideVol(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析接触网电压
	void DecodeNetSideA(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //网侧电流
	void DecodeStorageVol(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析蓄电池电压
	void DecodeSylinder(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //缸压
	void DecodeParsePipe(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //管压
	void DecodePull(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //牵引力和再生制动力
	void DecodeReBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //牵引力和再生制动力
	void DecodeAirBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //空气制动率
	void DecodeDirectionF(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);//提取方向手柄向前
	void DecodeDirectionB(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);//提取方向手柄向后
	void DecodeDevNum(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);//提取设备所在车号

	//插入二包数据
	void InsertWtdItem2(TrainFault  & fault, const unsigned char * data, size_t length);
	// 提取EOAS2包详细信息
	bool getInfo_FromUIC( unsigned int dwUIC , unsigned short& wTrainNo , unsigned char& uCarNum );
};
}

#endif /* WTD380BLPARSER_H_ */
