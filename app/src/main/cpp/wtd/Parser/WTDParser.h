/*
 * WTDParser.h
 *
 *  Created on: Apr 28, 2016
 *      Author: wangyong
 */

#ifndef WTDPARSER_H_
#define WTDPARSER_H_

#include "../wtd.h"
#include <string>
#include <map>
#include <vector>

namespace wtd
{
	typedef std::pair<unsigned int,std::string> GATEPAIR;

	class WTDParser
	{
	public:
		WTDParser();

		virtual ~WTDParser();

		virtual void Decode(WTD_ITEM1_TYPE type, const char * data, size_t length, std::map< WTD_ITEM1_TYPE, Status > & status) = 0;  //解析一包数据
		virtual void DecodeEoas2(const unsigned char * data, size_t length, std::vector< TrainFault > & fault) = 0;  //解析二包数据

		virtual void setTrainData(int trainId,bool is380B);
		virtual bool setWtd2Time(const unsigned char* pszData);

		virtual void InitEmapCodeErr();
		virtual void Init380BLmapCodeErr();
	protected:
		static std::map<std::string, GATEPAIR> m_GateMap;	// 门状态Map
		static std::map<int, std::string> m_EmapCodeErr;  //E26/E27故障文本初始化
		static std::map<int,std::string> m_380BLcodeDes; //380BL的故障代码
		int m_trainId;
		bool m_is380B;
		bool m_is16Emu;//是否为16编组，主要针对380BL的WTD类型
		unsigned int m_WTD2Time;   //二包故障时间
	};
}

#endif /* WTDPARSER_H_ */
