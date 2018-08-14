/*
 * WTDE26Parser.h
 *
 *  Created on: Apr 28, 2016
 *      Author: wangyong
 */

#ifndef WTDE26PARSER_H_
#define WTDE26PARSER_H_

#include "WTDParser.h"
#include <map>

namespace wtd
{
	class WTDE26Parser : public WTDParser
	{
	public:
		WTDE26Parser();
		virtual ~WTDE26Parser();

		virtual void Decode(WTD_ITEM1_TYPE type, const char * data, size_t length, std::map<WTD_ITEM1_TYPE,Status> & status);  //解析一包数据

		virtual void DecodeEoas2(const unsigned char * data, size_t length, std::vector< TrainFault > & fault);  //解析二包数据

	private:
		void DecodeUtcTime(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //解析UTC时间
		void DecodeDoor(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取车门的状态
		void DecodeOccupy(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取占用端
		void DecodeSpeed(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取动车组速度
		void DecodeGroup(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取编组信息
		void DecodePanto(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取受电弓状态
		void DecodeVCB(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status);   //提取主断信息
		void DecodeTowLevel(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取牵引手柄级位
		void DecodeBrakeLevel(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取制动手柄级位
		void DecodePB(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取停放制动信息
		void DecodeSplitPhase(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取自动过分相信息
		void DecodeBIN(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取BIN
		void DecodeEBSwitch(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取紧急制动拉动开关
		void DecodeBrakeInstruct(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取制动指令
		void DecodeDirectInstruct(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取方向指令
		void DecodeConstSpdState(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //提取恒速状态
		void DecodeStorageVol(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析蓄电池电压
		void DecodeSylinder(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //缸压
		void DecodePullElec(const char * data, size_t length, Status & s, std::map<WTD_ITEM1_TYPE,Status> & status); //解析牵引电流
		void DecodeReBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //再生制动力

		//插入二包数据
		void InsertWtdItem2(std::vector< TrainFault > & fault, unsigned int uiTrainDev, const unsigned char* pszData, const int iLen, const unsigned int uiStatMap[][3],const int iRowSize);
	};
}
#endif /* WTDE26PARSER_H_ */
