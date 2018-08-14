#include "PublicFun.h"
#include <stdlib.h>
#include <sstream>
#include <math.h>
#include <time.h>

namespace wtd
{
PublicFun::PublicFun(void)
{
}


PublicFun::~PublicFun(void)
{
}

/*
*@desc:从一个字节中的某几位（小于8）计算数据
*@param:oneByte，字节数
*@param:pos，在字节中的起始位置(0~7)
*@param:bits，在字节中的位数(pos+bits<=8)
*/
char PublicFun::ExtractNumFromByte(char oneByte, int pos, int bits)
{
	if(pos < 0 || pos > 7)
	{
		return 0;
	}
	if(bits < 0 || bits > 8)
	{
		return 0;
	}
	if(pos + bits > 8)
	{
		return 0;
	}
	int newPos = 8 - (pos + bits);
	return GetLenByBits(&oneByte, newPos, bits);
}

/*
* @desc：从pMem指向的内存的第pos位开始的bits位的数据大小
* @param：pMem，数据所在的内存
* @param：pos，在字节中的起始位置，自然顺序（高位到低位的序号为：0~7）
* @param：bits，数据使用的位数
*/
unsigned int PublicFun::GetLenByBits(char* pMem, int pos,int bits)
{
	if(pos < 0 || pos > 7)
	{
		return 0;
	}

	int cnt = bits-1;
	unsigned int ret = 0;
	unsigned int tmp = 0;
	char ch = *pMem;
	pos = 7 - pos;//转换成高位至低位的序号为7~0

	while(cnt >= 0)
	{
		tmp = (ch >> pos) & 1;
		ret += (tmp << cnt);
		cnt--;
		pos--;
		if(-1 == pos)
		{
			pos = 7;
			pMem++;
			ch = *pMem;
		}
	}
	return ret;
}

/**
 * 交换字符序
 * @param value 字符串值
 */
void PublicFun::SwapUINT16(unsigned short& value)
{
	unsigned short tmp = value;
	unsigned char* szValue = (unsigned char*)(&value);
	unsigned char* szTmp = (unsigned char*)(&tmp);
	szValue[0] = szTmp[1];
	szValue[1] = szTmp[0];
}

//! swap bit in the the bytes
/*!
	\param byte& brFirst the first byte to transfer
	\param const int ncFirstLocation the location to transfer
	\param byte& brSecond
	\param const int ncSecondLocation
	\param const int ncTransferLength the length to swap
*/
void PublicFun::SwapBitInByte(char & brFirst, const int ncFirstLocation, char & brSecond, const int ncSecondLocation, const int ncTransferLength)
{
	const int ncMask = (1 << ncTransferLength) - 1;
	const int ncFirstSwap = brFirst & (ncMask << (ncFirstLocation - ncTransferLength));
	const int ncSecondSwap = brSecond & (ncMask << (ncSecondLocation - ncTransferLength));
	const int ncDelta = ncFirstLocation - ncSecondLocation;

	brFirst = brFirst & ~(ncMask << (ncFirstLocation - ncTransferLength));
	brFirst += (ncDelta > 0) ? (ncSecondSwap << ncDelta) : (ncSecondSwap >> abs(ncDelta));

	brSecond = brSecond & ~(ncMask << (ncSecondLocation - ncTransferLength));
	brSecond += (ncDelta > 0) ? (ncFirstSwap >> ncDelta) : (ncFirstSwap << abs(ncDelta));
}

/**
 * 把解析到的数值赋值给satus
 * @param type WTD条目类型
 * @param value 值对应的枚举变量
 * @param desc 字符串值
 */
Status PublicFun::InitProcData(WTD_ITEM1_TYPE type,int value,const std::string & desc)
{
	Status _s;
	_s.type = type;
	_s.value = value;
	_s.desc = desc;
	return _s;
	
}

/**
 * 删除字符串末端分割符
 * @param str 字符串输入输出
 * @param symbol 默认为分号
 */

void PublicFun::eraseLastSymbol(std::string& str, std::string symbol)
{
	if(!str.empty() && str.substr(str.size()-1, 1) == symbol)
	{
		str.erase(str.size()-1, 1);
	}
}

std::string PublicFun::LongToStr(long value)
{
	std::string re = "";
	std::stringstream ss;
	ss << value;
	ss >> re;
	return re;
}

std::string PublicFun::ShortToStr(unsigned short value)
{
	std::string re = "";
	std::stringstream ss;
	ss << value;
	ss >> re;
	return re;
}

std::string PublicFun::IntToStr(int value)
{
	std::string re = "";
	std::stringstream ss;
	ss << value;
	ss >> re;
	return re;
}

/**
* 查看字节中的bit位是否为1
* param: pos,0~7
* return: true,第pos位为1；false，第pos位为0.
*/
bool PublicFun::IsBitOk(char num, int pos)
{
	if(pos >=0 && pos < 8)
	{
		switch(pos)
		{
		case 0:
			return ((num & 0x01) > 0);
		case 1:
			return ((num & 0x02) > 0);
		case 2:
			return ((num & 0x04) > 0);
		case 3:
			return ((num & 0x08) > 0);
		case 4:
			return ((num & 0x10) > 0);
		case 5:
			return ((num & 0x20) > 0);
		case 6:
			return ((num & 0x40) > 0);
		case 7:
			return ((num & 0x80) > 0);
		}
	}
	return false;
}

/*
*@desc: 四舍五入的方式把浮点数转换为字符串
*@param:palces,精度
*@param：bRound，是否四舍五入
*/
std::string PublicFun::FormatDouble(double value, int places, bool bRound /*= true*/)
{
	double power = pow((double)10, places);
	double temp = 0;
	if(bRound)
	{
		temp = (int)(value * power + 0.5) / power;
	}
	else
	{
		temp = (int)(value * power) / power;
	}

	std::stringstream ss;
	ss << temp;
	return ss.str();
}

unsigned short PublicFun::BitOrder(unsigned short value)
{
	unsigned short _value=value;
	_value= ((_value&0x00FF)<<8)|((_value&0xFF00)>>8);
	return _value;
}

/*!
* \brief RecordHelper::split 分割字符串
* \param str 要进行分割的字符串
* \param sep 作为分割点的字符串
* \return 返回分割后的字符串列表。str为空时返回空的列表；sep为空时认为不进行分割，返回仅包含str的字符串列表；
* str中不包含sep时不进行分割。
*/
std::vector<std::string> PublicFun::split(const std::string &str, const std::string &sep)
{
	std::vector<std::string> results;
	if(!str.empty())
	{
		if(sep.empty()|| str.find(sep) == std::string::npos)
		{
			results.push_back(str);
		}
		else
		{
			int start = 0;
			int end = str.find(sep, start);
			while(end != std::string::npos)
			{
				std::string sub = str.substr(start, end - start);
				start += sub.length() + sep.length();
				results.push_back(sub);
				end = str.find(sep, start);
			}
			//截取最后一段
			if(start < str.length())
			{
				results.push_back(str.substr(start, str.length() - start));
			}
			else
			{
				results.push_back("");//以分隔符结尾的字符串，分隔完后要追加一个空字符串。
			}
		}
	}
	return results;
}

std::string PublicFun::CharTog(const char * first, const char * second,size_t length)
{
	char * _data = new char[length];
	memset(_data,0,length);
	strcat(_data,first);
	strcat(_data,second);
	return _data;
}



std::string  PublicFun::InsertChar(const std::string & str,int num,const char & car)
{
	char * _data = new char[str.length() + 1];
	const char * _cstr = str.c_str();
	bool _isInsert = false;
	for (int i = 0;i < (int)str.length() + 1; i++)
	{
		if (i == num)
		{
			_data[i] = car;
			_isInsert = true;
			continue;
		}
		if (_isInsert)
		{
			_data[i] = _cstr[i-1];
		}
		else
		{
			_data[i] = _cstr[i];
		}
		 
	}

	return _data;
}

template <class T>
void PublicFun::InsertVector(std::vector< T > & vec1, std::vector< T > & vec2)
{
	for (int i = 0;i < vec2.size(); i++)
	{
		vec1.push_back(vec2[i]);
	}
}

int PublicFun::BCDTo_10(char value)
{
	int High_byte = (value&0xF0)>>4;
	int Low_byte  = (value&0x0F);

	return (High_byte*10+Low_byte);
}

std::string PublicFun::LongToTime(long value)
{
	//新添加 加上
	time_t _t;
	value += 8*60*60;
	_t = value;
	std::string str_result = "";
	std::string str_tmp = "";
	struct tm *p;
	p = gmtime(&_t);
	str_result += IntToStr(p->tm_year+1900);      //year
	str_result += "-";
	if((1+p->tm_mon)<10)
	{
		str_tmp += "0";
		str_tmp += IntToStr(1+p->tm_mon);
	}
	else
	{
		str_tmp += IntToStr(1+p->tm_mon);
	}
	str_result += str_tmp;                    //month
	str_result += "-";
	str_tmp = "";
	if(p->tm_mday<10)
	{
		str_tmp += "0";
		str_tmp += IntToStr(p->tm_mday);
	}
	else
	{
		str_tmp += IntToStr(p->tm_mday);
	}
	str_result += str_tmp;                    //day

	str_result += " ";

	str_tmp = "";
	if(p->tm_hour<10)
	{
		str_tmp += "0";
		str_tmp += IntToStr(p->tm_hour);
	}
	else
	{
		str_tmp += IntToStr(p->tm_hour);
	}

	str_result +=  str_tmp;                //hour
	str_result += ":";
	str_tmp = "";
	if(p->tm_min<10)
	{
		str_tmp += "0";
		str_tmp += IntToStr(p->tm_min);
	}
	else
	{
		str_tmp += IntToStr(p->tm_min);
	}
	str_result +=  str_tmp;                //min
	str_result += ":";

	str_tmp = "";
	if(p->tm_sec<10)
	{
		str_tmp += "0";
		str_tmp += IntToStr(p->tm_sec);
	}
	else
	{
		str_tmp += IntToStr(p->tm_sec);
	}
	str_result +=  str_tmp;                //min

	return  str_result;
}

}
