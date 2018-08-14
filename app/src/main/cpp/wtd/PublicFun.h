#ifndef PUBLICFUN_H
#define PUBLICFUN_H
/*
 * PublicFun.h
 * 供外部访问的公共成员变量
 *  Created on: 2016-5-19
 *      Author: szf
 */

#include "wtd.h"
#include <vector>
#include <string>

namespace wtd
{

	class PublicFun
	{
	public:
		PublicFun(void);
		~PublicFun(void);

		static char ExtractNumFromByte(char oneByte, int pos, int bits);
		static unsigned int GetLenByBits(char* pMem, int pos,int bits);
		static void SwapUINT16(unsigned short& value);						// 交换字节序
		static void SwapBitInByte( char & brFirst, const int ncFirstLocation, char & brSecond, const int ncSecondLocation, const int ncTransferLength);

		static Status InitProcData(WTD_ITEM1_TYPE type,int value,const std::string & desc);
		// 删除字符串末端分割符
	    static void eraseLastSymbol(std::string& str, std::string symbol=";");
		//long转string
	    static std::string LongToStr(long value);
		//int转string
		static std::string IntToStr(int value);
		//short转string
		static std::string ShortToStr(unsigned short value);
		// 查看字节中的bit位是否为1
		static bool IsBitOk(char num, int pos);
	
        //四舍五入的方式把浮点数转换为字符串
		static std::string FormatDouble(double value, int places, bool bRound = true);
		//short型数据高低字节小端转换
	    static unsigned short BitOrder(unsigned short value);
		//字符串分割
	    static std::vector<std::string> split(const std::string &str, const std::string &sep);
		//字节合并，合并到低位
		static std::string CharTog( const char * first, const char * second,size_t length);
        //在字符串的某个位置插入字节
		static std::string InsertChar(const std::string & str,int num,const char & car);
		//在一个Vector后面插入一个Vector
		template <class T>
		static void InsertVector(std::vector< T > & vec1, std::vector< T > & vec2);
		//BCD 转换为十进制数据
		static int  BCDTo_10(char value);
		//时间戳转换为标准时间函数
		static std::string LongToTime(long value);
	    
	};


	
}


#endif


