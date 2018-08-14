/*
 * WTD5GParser.h
 *
 *  Created on: Apr 28, 2016
 *      Author: wangyong
 */

#ifndef WTD5GPARSER_H_
#define WTD5GPARSER_H_

#include "WTDParser.h"
#include <vector>
#include <map>

namespace wtd
{
	class WTD5GParser : public WTDParser
	{
	public:
		WTD5GParser();
		virtual ~WTD5GParser();

		virtual void Decode(WTD_ITEM1_TYPE type, const char * data, size_t length, std::map<WTD_ITEM1_TYPE,Status> & status);  //解析一包数据
		virtual void DecodeEoas2(const unsigned char * data, size_t length, std::vector< TrainFault > & fault);  //解析二包数据

	private:
		void ReverseByDelimeter(std::string & value);   //物理车厢和逻辑车厢转换
		//逻辑车厢转换为物理车厢
		void ConvertUnionEnd_1(std::string & value);//重联1端占用
		void ConvertUnionEnd_8(std::string & value);//重联8端占用

		void getRemitState(const unsigned char state, std::ostringstream& oss, bool bflag=false);	// 解析停放制动状态位
		void getPantoState(const unsigned char state, std::ostringstream& oss, bool bflag=false);	// 解析升降弓状态位
		void getVCBState(const unsigned char state, std::ostringstream& oss, bool bflag=false);		// 解析主断路器状态位
		void getStdByBrake(const unsigned char state, std::ostringstream& oss, bool bflag=false);	// 解析备制投入状态位
		void getAlertState(const unsigned char state, std::ostringstream& oss, bool bflag=false);    //解析司机警惕装置状态
		const char* getDoorState(char num);		// 解析车门状态位
		
		bool TransferLogicalCabinToRealCabin( char * pValue, std::map<WTD_ITEM1_TYPE,Status> & status);//逻辑车厢转换为物理车厢，针对车门状态
		void SwapBitInByte( char & brFirst, const int ncFirstLocation, char & brSecond, const int ncSecondLocation, const int ncTransferLength);

		void DecodeDoor(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析车门状态
		void DecodeSpeed(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析动车组速度
		void DecodeGroup(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //提取动车组编组
		void DecodePanto(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);  //提取受电弓状态
		void DecodeVCB(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);  //提取主断路器状态
		void DecodeTowLevel(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //提取动车组牵引级位
		void DecodeBrakeLevel(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //提取动车组制动级位
		void DecodeRemit(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status);  //提取停放制动状态
		void DecodeSplitPhase(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //自动过分相装置断、合电信息GFX-3
		void DecodeDriverDesk(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //司机台启用状态
		void DecodeDDU(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //DDU所在物理车厢号
		void DecodeConstSpd(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //提取恒速速度
		void DecodeConstSpdState(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //提取恒速状态
		void DecodeStdByBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //提取备制投入状态
		void DecodeNetSideVol(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析接触网电压
		void DecodeNetSideA(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //网侧电流
		void DecodePipePress(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //管压
		void DecodeSylinderPress(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //缸压
		void DecodePullElec(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析牵引电流
		void DecodeReBrake(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //再生制动力
		void DecodeRunDir(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析方向手柄
		void DecodeAlertState(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析司机警惕状态
		void DecodeUtcTime(const char * data, size_t length, Status & s,std::map<WTD_ITEM1_TYPE,Status> & status); //解析UTC时间

	};
}


#endif /* WTD5GPARSER_H_ */
