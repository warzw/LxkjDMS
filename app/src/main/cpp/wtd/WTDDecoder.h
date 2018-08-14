/*
 * WTDDecoder.h
 *
 *  Created on: Apr 26, 2016
 *      Author: wangyong
 */

#ifndef WTDDECODER_H_
#define WTDDECODER_H_

#include <string>
#include <vector>
#include <map>

#include "wtd.h"

namespace wtd
{
	class RTWTDDecoder
	{
	public:
		RTWTDDecoder(){}
		virtual ~RTWTDDecoder(){}
		virtual bool DecodeRTWTDStatus1(const char * data,size_t length, std::map<WTD_ITEM1_TYPE,Status> & status) = 0; //解析EOAS1包数据 
		virtual bool DecodeRTWTDStatus1_V0(const char * data,size_t length, std::map<WTD_ITEM1_TYPE,Status> & status) = 0; //解析EOAS1包数据 
		virtual	bool DecodeRTWTDStatus2(const unsigned char * data,size_t length, std::vector< TrainFault > & fault) = 0; //处理二包数据
		virtual void SetTrainData(int trainId, bool is380B = false)
		{
			m_trainId = trainId;
			m_is380B = is380B;
		}
	protected:
		int m_trainId;
		bool m_is380B;
	};


}

namespace wtd
{
	class TQWTDDecoder
	{
	public:
		TQWTDDecoder()
		{

		}
		virtual ~TQWTDDecoder()
		{

		}
		virtual bool DecodeTQWTDStatus1(const char * data,std::map<WTD_ITEM1_TYPE,Status> & status) = 0; //解析EOAS1包数据
		virtual	bool DecodeTQWTDStatus2(const unsigned char * data,size_t length, std::vector< TrainFault > & fault) = 0; //处理二包数据
		virtual void SetTrainData(int trainId, bool is380B = false)
		{
			m_trainId = trainId;
			m_is380B = is380B;
		}
	protected:
		int m_trainId;
		bool m_is380B;

	};

}

#endif /* WTDDECODER_H_ */
