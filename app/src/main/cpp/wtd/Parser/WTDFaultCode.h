﻿/*
 * WTDFaultCode.h
 *
 *  Created on: Jun 1, 2016
 *      Author: songzhenfei
 */

//解析WTD数据二包故障表
#ifndef WTDFAULTCODE_H
#define WTDFAULTCODE_H
#include "../wtd.h"
#include <map>

namespace wtd
{

	//E27故障代码
	//1号车中央1系
	const unsigned int m_E27uiNo01Center1[][3] =
	{
		{15*8+0,657,SYS_NETCONTROL},
		{15*8+1,661,SYS_NETCONTROL},
		{15*8+2,665,SYS_NETCONTROL},
		{15*8+3,666,SYS_NETCONTROL},
		{15*8+4,695,SYS_NETCONTROL},
		{15*8+6,194,SYS_UNKNOW},
		{18*8+0,830,SYS_NETCONTROL},
		{18*8+1,831,SYS_NETCONTROL},
		{18*8+2,832,SYS_NETCONTROL},
		{18*8+3,833,SYS_NETCONTROL},
		{18*8+4,834,SYS_NETCONTROL},
		{18*8+5,835,SYS_NETCONTROL},
		{18*8+6,836,SYS_NETCONTROL},
		{18*8+7,837,SYS_NETCONTROL},
		{19*8+0,838,SYS_NETCONTROL},
		{19*8+1,839,SYS_NETCONTROL},
		{19*8+2,840,SYS_NETCONTROL},
		{19*8+3,841,SYS_NETCONTROL},
		{19*8+4,850,SYS_NETCONTROL},
		{19*8+5,851,SYS_NETCONTROL},
		{19*8+6,852,SYS_NETCONTROL},
		{19*8+7,853,SYS_NETCONTROL},
		{20*8+0,854,SYS_NETCONTROL},
		{20*8+1,855,SYS_NETCONTROL},
		{20*8+2,856,SYS_NETCONTROL},
		{20*8+3,857,SYS_NETCONTROL},
		{20*8+4,858,SYS_NETCONTROL},
		{20*8+5,859,SYS_NETCONTROL},
		{20*8+6,860,SYS_NETCONTROL},
		{20*8+7,861,SYS_NETCONTROL},
		{23*8+0,826,SYS_NETCONTROL}
	};

	//1号车中央2系
	const unsigned int m_E27uiNo01Center2[][3] =
	{
		{28*8+4,696,SYS_NETCONTROL},
		{30*8+0,830,SYS_NETCONTROL},
		{30*8+1,831,SYS_NETCONTROL},
		{30*8+2,832,SYS_NETCONTROL},
		{30*8+3,833,SYS_NETCONTROL},
		{30*8+4,834,SYS_NETCONTROL},
		{30*8+5,835,SYS_NETCONTROL},
		{30*8+6,836,SYS_NETCONTROL},
		{30*8+7,837,SYS_NETCONTROL},
		{31*8+0,838,SYS_NETCONTROL},
		{31*8+1,839,SYS_NETCONTROL},
		{31*8+2,840,SYS_NETCONTROL},
		{31*8+3,841,SYS_NETCONTROL},
		{31*8+4,850,SYS_NETCONTROL},
		{31*8+5,851,SYS_NETCONTROL},
		{31*8+6,852,SYS_NETCONTROL},
		{31*8+7,853,SYS_NETCONTROL},
		{32*8+0,854,SYS_NETCONTROL},
		{32*8+1,855,SYS_NETCONTROL},
		{32*8+2,856,SYS_NETCONTROL},
		{32*8+3,857,SYS_NETCONTROL},
		{32*8+4,858,SYS_NETCONTROL},
		{32*8+5,859,SYS_NETCONTROL},
		{32*8+6,860,SYS_NETCONTROL},
		{32*8+7,861,SYS_NETCONTROL}

	};

	//8号车中央1系
	const unsigned int m_E27uiNo08Center1[][3] =
	{
		{37*8+4,695,SYS_NETCONTROL},
		{37*8+6,194,SYS_UNKNOW},
		{40*8+0,830,SYS_NETCONTROL},
		{40*8+1,831,SYS_NETCONTROL},
		{40*8+2,832,SYS_NETCONTROL},
		{40*8+3,833,SYS_NETCONTROL},
		{40*8+4,834,SYS_NETCONTROL},
		{40*8+5,835,SYS_NETCONTROL},
		{40*8+6,836,SYS_NETCONTROL},
		{40*8+7,837,SYS_NETCONTROL},
		{41*8+0,838,SYS_NETCONTROL},
		{41*8+1,839,SYS_NETCONTROL},
		{41*8+2,840,SYS_NETCONTROL},
		{41*8+3,841,SYS_NETCONTROL},
		{41*8+4,850,SYS_NETCONTROL},
		{41*8+5,851,SYS_NETCONTROL},
		{41*8+6,852,SYS_NETCONTROL},
		{41*8+7,853,SYS_NETCONTROL},
		{42*8+0,854,SYS_NETCONTROL},
		{42*8+1,855,SYS_NETCONTROL},
		{42*8+2,856,SYS_NETCONTROL},
		{42*8+3,857,SYS_NETCONTROL},
		{42*8+4,858,SYS_NETCONTROL},
		{42*8+5,859,SYS_NETCONTROL},
		{42*8+6,860,SYS_NETCONTROL},
		{42*8+7,861,SYS_NETCONTROL}
	};
	//8号车中央2系
	const unsigned int m_E27uiNo08Center2[][3] =
	{
		{45*8+0,826,SYS_NETCONTROL},
		{50*8+4,696,SYS_NETCONTROL},
		{52*8+0,830,SYS_NETCONTROL},
		{52*8+1,831,SYS_NETCONTROL},
		{52*8+2,832,SYS_NETCONTROL},
		{52*8+3,833,SYS_NETCONTROL},
		{52*8+4,834,SYS_NETCONTROL},
		{52*8+5,835,SYS_NETCONTROL},
		{52*8+6,836,SYS_NETCONTROL},
		{52*8+7,837,SYS_NETCONTROL},
		{53*8+0,838,SYS_NETCONTROL},
		{53*8+1,839,SYS_NETCONTROL},
		{53*8+2,840,SYS_NETCONTROL},
		{53*8+3,841,SYS_NETCONTROL},
		{53*8+4,850,SYS_NETCONTROL},
		{53*8+5,851,SYS_NETCONTROL},
		{53*8+6,852,SYS_NETCONTROL},
		{53*8+7,853,SYS_NETCONTROL},
		{54*8+0,854,SYS_NETCONTROL},
		{54*8+1,855,SYS_NETCONTROL},
		{54*8+2,856,SYS_NETCONTROL},
		{54*8+3,857,SYS_NETCONTROL},
		{54*8+4,858,SYS_NETCONTROL},
		{54*8+5,859,SYS_NETCONTROL},
		{54*8+6,860,SYS_NETCONTROL},
		{54*8+7,861,SYS_NETCONTROL}

	};

	//1号车终端
	const unsigned int m_E27uiNo01Terminal[][3] =
	{
		{62*8+1,52,SYS_AIRSUPPLY},
		{62*8+2,59,SYS_AIRSUPPLY},
		{62*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{62*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{62*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{62*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{62*8+7,204,SYS_POWERSUPPLY},
		{69*8+0,830,SYS_NETCONTROL},
		{69*8+1,831,SYS_NETCONTROL},
		{69*8+2,832,SYS_NETCONTROL},
		{69*8+3,833,SYS_NETCONTROL},
		{69*8+4,834,SYS_NETCONTROL},
		{69*8+5,835,SYS_NETCONTROL},
		{69*8+6,836,SYS_NETCONTROL},
		{69*8+7,837,SYS_NETCONTROL},
		{70*8+0,838,SYS_NETCONTROL},
		{70*8+1,839,SYS_NETCONTROL},
		{70*8+2,840,SYS_NETCONTROL},
		{70*8+3,841,SYS_NETCONTROL},
		{70*8+4,850,SYS_NETCONTROL},
		{70*8+5,851,SYS_NETCONTROL},
		{70*8+6,852,SYS_NETCONTROL},
		{70*8+7,853,SYS_NETCONTROL},
		{71*8+0,854,SYS_NETCONTROL},
		{71*8+1,855,SYS_NETCONTROL},
		{71*8+2,856,SYS_NETCONTROL},
		{71*8+3,857,SYS_NETCONTROL},
		{71*8+4,858,SYS_NETCONTROL},
		{71*8+5,859,SYS_NETCONTROL},
		{71*8+6,860,SYS_NETCONTROL},
		{71*8+7,861,SYS_NETCONTROL},
		{74*8+0,123,SYS_AIRSUPPLY},
		{74*8+1,135,SYS_POWERSUPPLY},
		{74*8+7,151,SYS_AIRSUPPLY},
		{75*8+0,152,SYS_AIRSUPPLY},
		{75*8+1,153,SYS_AIRSUPPLY},
		{75*8+2,154,SYS_AIRSUPPLY},
		{75*8+3,155,SYS_AIRSUPPLY},
		{75*8+4,166,SYS_POWERSUPPLY},
		{75*8+5,143,SYS_POWERSUPPLY},
		{75*8+6,586,SYS_AIRSUPPLY},
		{76*8+3,490,SYS_AIRSUPPLY},
		{76*8+4,491,SYS_AIRSUPPLY},
		{76*8+5,492,SYS_AIRSUPPLY},
		{76*8+6,493,SYS_AIRSUPPLY},
		{76*8+7,82,SYS_UNKNOW},
		{77*8+4,83,SYS_UNKNOW},
		{77*8+5,266,SYS_UNKNOW},
		{77*8+6,265,SYS_UNKNOW},
		{82*8+2,108,SYS_TRAINDEVICE},
		{82*8+3,109,SYS_TRAINDEVICE}

	};

	//2号车终端
	const unsigned int m_E27uiNo02Terminal[][3] =
	{
		{92*8+0,2,SYS_TRACTIONDRIVE},
		{92*8+1,52,SYS_AIRSUPPLY},
		{92*8+2,59,SYS_AIRSUPPLY},
		{92*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{92*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{92*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{92*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{93*8+1,266,SYS_UNKNOW},
		{93*8+2,265,SYS_UNKNOW},
		{96*8+0,830,SYS_NETCONTROL},
		{96*8+1,831,SYS_NETCONTROL},
		{96*8+2,832,SYS_NETCONTROL},
		{96*8+3,833,SYS_NETCONTROL},
		{96*8+4,834,SYS_NETCONTROL},
		{96*8+5,835,SYS_NETCONTROL},
		{96*8+6,836,SYS_NETCONTROL},
		{96*8+7,837,SYS_NETCONTROL},
		{97*8+0,838,SYS_NETCONTROL},
		{97*8+1,839,SYS_NETCONTROL},
		{97*8+2,840,SYS_NETCONTROL},
		{97*8+3,841,SYS_NETCONTROL},
		{97*8+4,850,SYS_NETCONTROL},
		{97*8+5,851,SYS_NETCONTROL},
		{97*8+6,852,SYS_NETCONTROL},
		{97*8+7,853,SYS_NETCONTROL},
		{98*8+0,854,SYS_NETCONTROL},
		{98*8+1,855,SYS_NETCONTROL},
		{98*8+2,856,SYS_NETCONTROL},
		{98*8+3,857,SYS_NETCONTROL},
		{98*8+4,858,SYS_NETCONTROL},
		{98*8+5,859,SYS_NETCONTROL},
		{98*8+6,860,SYS_NETCONTROL},
		{98*8+7,861,SYS_NETCONTROL},
		{99*8+2,490,SYS_AIRSUPPLY},
		{99*8+3,491,SYS_AIRSUPPLY},
		{99*8+4,492,SYS_AIRSUPPLY},
		{99*8+5,493,SYS_AIRSUPPLY},
		{99*8+6,156,SYS_TRACTIONDRIVE},
		{100*8+0,123,SYS_AIRSUPPLY},
		{100*8+1,4,SYS_TRACTIONDRIVE},
		{100*8+2,5,SYS_TRACTIONDRIVE},
		{100*8+3,134,SYS_TRACTIONDRIVE},
		{100*8+4,137,SYS_TRACTIONDRIVE},
		{100*8+5,138,SYS_TRACTIONDRIVE},
		{100*8+6,139,SYS_TRACTIONDRIVE},
		{100*8+7,151,SYS_AIRSUPPLY},
		{101*8+0,152,SYS_AIRSUPPLY},
		{101*8+1,153,SYS_AIRSUPPLY},
		{101*8+2,154,SYS_AIRSUPPLY},
		{101*8+3,155,SYS_AIRSUPPLY},
		{101*8+4,141,SYS_TRACTIONDRIVE},
		{101*8+5,142,SYS_TRACTIONDRIVE},
		{101*8+6,170,SYS_POWERSUPPLY},
		{101*8+7,586,SYS_AIRSUPPLY},
		{102*8+0,162,SYS_TRACTIONDRIVE},
		{102*8+1,163,SYS_TRACTIONDRIVE},
		{102*8+2,164,SYS_TRACTIONDRIVE},
		{102*8+3,165,SYS_TRACTIONDRIVE},
		{106*8+2,108,SYS_TRAINDEVICE},
		{106*8+3,109,SYS_TRAINDEVICE},
		{106*8+4,110,SYS_TRAINDEVICE},
		{106*8+5,111,SYS_TRAINDEVICE}

	};

	//3号车终端
	const unsigned int m_E27uiNo03Terminal[][3] =
	{
		{116*8+0,2,SYS_TRACTIONDRIVE},
		{116*8+1,52,SYS_AIRSUPPLY},
		{116*8+2,59,SYS_AIRSUPPLY},
		{116*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{116*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{116*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{116*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{117*8+1,266,SYS_UNKNOW},
		{117*8+2,265,SYS_UNKNOW},
		{120*8+0,830,SYS_NETCONTROL},
		{120*8+1,831,SYS_NETCONTROL},
		{120*8+2,832,SYS_NETCONTROL},
		{120*8+3,833,SYS_NETCONTROL},
		{120*8+4,834,SYS_NETCONTROL},
		{120*8+5,835,SYS_NETCONTROL},
		{120*8+6,836,SYS_NETCONTROL},
		{120*8+7,837,SYS_NETCONTROL},
		{121*8+0,838,SYS_NETCONTROL},
		{121*8+1,839,SYS_NETCONTROL},
		{121*8+2,840,SYS_NETCONTROL},
		{121*8+3,841,SYS_NETCONTROL},
		{121*8+4,850,SYS_NETCONTROL},
		{121*8+5,851,SYS_NETCONTROL},
		{121*8+6,852,SYS_NETCONTROL},
		{121*8+7,853,SYS_NETCONTROL},
		{122*8+0,854,SYS_NETCONTROL},
		{122*8+1,855,SYS_NETCONTROL},
		{122*8+2,856,SYS_NETCONTROL},
		{122*8+3,857,SYS_NETCONTROL},
		{122*8+4,858,SYS_NETCONTROL},
		{122*8+5,859,SYS_NETCONTROL},
		{122*8+6,860,SYS_NETCONTROL},
		{122*8+7,861,SYS_NETCONTROL},
		{123*8+2,490,SYS_AIRSUPPLY},
		{123*8+3,491,SYS_AIRSUPPLY},
		{123*8+4,492,SYS_AIRSUPPLY},
		{123*8+5,493,SYS_AIRSUPPLY},
		{123*8+6,156,SYS_TRACTIONDRIVE},
		{123*8+7,80,SYS_UNKNOW},
		{124*8+0,123,SYS_AIRSUPPLY},
		{124*8+1,4,SYS_TRACTIONDRIVE},
		{124*8+2,5,SYS_TRACTIONDRIVE},
		{124*8+3,134,SYS_TRACTIONDRIVE},
		{124*8+4,137,SYS_TRACTIONDRIVE},
		{124*8+5,138,SYS_TRACTIONDRIVE},
		{124*8+6,139,SYS_TRACTIONDRIVE},
		{124*8+7,151,SYS_AIRSUPPLY},
		{125*8+0,152,SYS_AIRSUPPLY},
		{125*8+1,153,SYS_AIRSUPPLY},
		{125*8+2,154,SYS_AIRSUPPLY},
		{125*8+3,155,SYS_AIRSUPPLY},
		{125*8+4,141,SYS_TRACTIONDRIVE},
		{125*8+5,142,SYS_TRACTIONDRIVE},
		{125*8+7,586,SYS_AIRSUPPLY},
		{130*8+2,108,SYS_TRAINDEVICE},
		{130*8+3,109,SYS_TRAINDEVICE},
		{130*8+4,110,SYS_TRAINDEVICE},
		{130*8+5,111,SYS_TRAINDEVICE},
		{131*8+7,82,SYS_UNKNOW}
	};

	//4号车终端
	const unsigned int m_E27uiNo04Terminal[][3] =
	{
		{139*8+0,2,SYS_TRACTIONDRIVE},
		{139*8+1,52,SYS_AIRSUPPLY},
		{139*8+2,59,SYS_AIRSUPPLY},
		{139*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{139*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{139*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{139*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{140*8+1,266,SYS_UNKNOW},
		{140*8+2,265,SYS_UNKNOW},
		{143*8+0,830,SYS_NETCONTROL},
		{143*8+1,831,SYS_NETCONTROL},
		{143*8+2,832,SYS_NETCONTROL},
		{143*8+3,833,SYS_NETCONTROL},
		{143*8+4,834,SYS_NETCONTROL},
		{143*8+5,835,SYS_NETCONTROL},
		{143*8+6,836,SYS_NETCONTROL},
		{143*8+7,837,SYS_NETCONTROL},
		{144*8+0,838,SYS_NETCONTROL},
		{144*8+1,839,SYS_NETCONTROL},
		{144*8+2,840,SYS_NETCONTROL},
		{144*8+3,841,SYS_NETCONTROL},
		{144*8+4,850,SYS_NETCONTROL},
		{144*8+5,851,SYS_NETCONTROL},
		{144*8+6,852,SYS_NETCONTROL},
		{144*8+7,853,SYS_NETCONTROL},
		{145*8+0,854,SYS_NETCONTROL},
		{145*8+1,855,SYS_NETCONTROL},
		{145*8+2,856,SYS_NETCONTROL},
		{145*8+3,857,SYS_NETCONTROL},
		{145*8+4,858,SYS_NETCONTROL},
		{145*8+5,859,SYS_NETCONTROL},
		{145*8+6,860,SYS_NETCONTROL},
		{145*8+7,861,SYS_NETCONTROL},
		{146*8+2,490,SYS_AIRSUPPLY},
		{146*8+3,491,SYS_AIRSUPPLY},
		{146*8+4,492,SYS_AIRSUPPLY},
		{146*8+5,493,SYS_AIRSUPPLY},
		{146*8+6,156,SYS_TRACTIONDRIVE},
		{147*8+0,123,SYS_AIRSUPPLY},
		{147*8+1,4,SYS_TRACTIONDRIVE},
		{147*8+2,5,SYS_TRACTIONDRIVE},
		{147*8+3,134,SYS_TRACTIONDRIVE},
		{147*8+4,137,SYS_TRACTIONDRIVE},
		{147*8+5,138,SYS_TRACTIONDRIVE},
		{147*8+6,139,SYS_TRACTIONDRIVE},
		{147*8+7,151,SYS_AIRSUPPLY},
		{148*8+0,152,SYS_AIRSUPPLY},
		{148*8+1,153,SYS_AIRSUPPLY},
		{148*8+2,154,SYS_AIRSUPPLY},
		{148*8+3,155,SYS_AIRSUPPLY},
		{148*8+4,141,SYS_TRACTIONDRIVE},
		{148*8+5,142,SYS_TRACTIONDRIVE},
		{148*8+7,586,SYS_AIRSUPPLY},
		{149*8+0,162,SYS_TRACTIONDRIVE},
		{149*8+1,163,SYS_TRACTIONDRIVE},
		{149*8+2,164,SYS_TRACTIONDRIVE},
		{149*8+3,165,SYS_TRACTIONDRIVE},
		{149*8+4,682,SYS_POWERSUPPLY},
		{153*8+2,108,SYS_TRAINDEVICE},
		{153*8+3,109,SYS_TRAINDEVICE},
		{153*8+4,110,SYS_TRAINDEVICE},
		{153*8+5,111,SYS_TRAINDEVICE}

	};

	//5号车终端
	const unsigned int m_E27uiNo05Terminal[][3] =
	{
		{163*8+0,2,SYS_TRACTIONDRIVE},
		{163*8+1,52,SYS_AIRSUPPLY},
		{163*8+2,59,SYS_AIRSUPPLY},
		{163*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{163*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{163*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{163*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{163*8+7,204,SYS_POWERSUPPLY},
		{164*8+1,266,SYS_UNKNOW},
		{164*8+2,265,SYS_UNKNOW},
		{167*8+0,830,SYS_NETCONTROL},
		{167*8+1,831,SYS_NETCONTROL},
		{167*8+2,832,SYS_NETCONTROL},
		{167*8+3,833,SYS_NETCONTROL},
		{167*8+4,834,SYS_NETCONTROL},
		{167*8+5,835,SYS_NETCONTROL},
		{167*8+6,836,SYS_NETCONTROL},
		{167*8+7,837,SYS_NETCONTROL},
		{168*8+0,838,SYS_NETCONTROL},
		{168*8+1,839,SYS_NETCONTROL},
		{168*8+2,840,SYS_NETCONTROL},
		{168*8+3,841,SYS_NETCONTROL},
		{168*8+4,850,SYS_NETCONTROL},
		{168*8+5,851,SYS_NETCONTROL},
		{168*8+6,852,SYS_NETCONTROL},
		{168*8+7,853,SYS_NETCONTROL},
		{169*8+0,854,SYS_NETCONTROL},
		{169*8+1,855,SYS_NETCONTROL},
		{169*8+2,856,SYS_NETCONTROL},
		{169*8+3,857,SYS_NETCONTROL},
		{169*8+4,858,SYS_NETCONTROL},
		{169*8+5,859,SYS_NETCONTROL},
		{169*8+6,860,SYS_NETCONTROL},
		{169*8+7,861,SYS_NETCONTROL},
		{170*8+2,490,SYS_AIRSUPPLY},
		{170*8+3,491,SYS_AIRSUPPLY},
		{170*8+4,492,SYS_AIRSUPPLY},
		{170*8+5,493,SYS_AIRSUPPLY},
		{170*8+6,156,SYS_TRACTIONDRIVE},
		{171*8+0,123,SYS_AIRSUPPLY},
		{171*8+1,4,SYS_TRACTIONDRIVE},
		{171*8+2,5,SYS_TRACTIONDRIVE},
		{171*8+3,134,SYS_TRACTIONDRIVE},
		{171*8+4,137,SYS_TRACTIONDRIVE},
		{171*8+5,138,SYS_TRACTIONDRIVE},
		{171*8+6,139,SYS_TRACTIONDRIVE},
		{171*8+7,151,SYS_AIRSUPPLY},
		{172*8+0,152,SYS_AIRSUPPLY},
		{172*8+1,153,SYS_AIRSUPPLY},
		{172*8+2,154,SYS_AIRSUPPLY},
		{172*8+3,155,SYS_AIRSUPPLY},
		{172*8+4,141,SYS_TRACTIONDRIVE},
		{172*8+5,142,SYS_TRACTIONDRIVE},
		{172*8+7,586,SYS_AIRSUPPLY},
		{173*8+5,166,SYS_POWERSUPPLY},
		{173*8+6,143,SYS_POWERSUPPLY},
		{173*8+7,135,SYS_POWERSUPPLY}

	};

	//6号车终端
	const unsigned int m_E27uiNo06Terminal[][3] =
	{
		{186*8+0,2,SYS_TRACTIONDRIVE},
		{186*8+1,52,SYS_AIRSUPPLY},
		{186*8+2,59,SYS_AIRSUPPLY},
		{186*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{186*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{186*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{186*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{187*8+1,266,SYS_UNKNOW},
		{187*8+2,265,SYS_UNKNOW},
		{190*8+0,830,SYS_NETCONTROL},
		{190*8+1,831,SYS_NETCONTROL},
		{190*8+2,832,SYS_NETCONTROL},
		{190*8+3,833,SYS_NETCONTROL},
		{190*8+4,834,SYS_NETCONTROL},
		{190*8+5,835,SYS_NETCONTROL},
		{190*8+6,836,SYS_NETCONTROL},
		{190*8+7,837,SYS_NETCONTROL},
		{191*8+0,838,SYS_NETCONTROL},
		{191*8+1,839,SYS_NETCONTROL},
		{191*8+2,840,SYS_NETCONTROL},
		{191*8+3,841,SYS_NETCONTROL},
		{191*8+4,850,SYS_NETCONTROL},
		{191*8+5,851,SYS_NETCONTROL},
		{191*8+6,852,SYS_NETCONTROL},
		{191*8+7,853,SYS_NETCONTROL},
		{192*8+0,854,SYS_NETCONTROL},
		{192*8+1,855,SYS_NETCONTROL},
		{192*8+2,856,SYS_NETCONTROL},
		{192*8+3,857,SYS_NETCONTROL},
		{192*8+4,858,SYS_NETCONTROL},
		{192*8+5,859,SYS_NETCONTROL},
		{192*8+6,860,SYS_NETCONTROL},
		{192*8+7,861,SYS_NETCONTROL},
		{193*8+2,490,SYS_AIRSUPPLY},
		{193*8+3,491,SYS_AIRSUPPLY},
		{193*8+4,492,SYS_AIRSUPPLY},
		{193*8+5,493,SYS_AIRSUPPLY},
		{193*8+6,156,SYS_TRACTIONDRIVE},
		{194*8+0,123,SYS_AIRSUPPLY},
		{194*8+1,4,SYS_TRACTIONDRIVE},
		{194*8+2,5,SYS_TRACTIONDRIVE},
		{194*8+3,134,SYS_TRACTIONDRIVE},
		{194*8+4,137,SYS_TRACTIONDRIVE},
		{194*8+5,138,SYS_TRACTIONDRIVE},
		{194*8+6,139,SYS_TRACTIONDRIVE},
		{194*8+7,151,SYS_AIRSUPPLY},
		{195*8+0,152,SYS_AIRSUPPLY},
		{195*8+1,153,SYS_AIRSUPPLY},
		{195*8+2,154,SYS_AIRSUPPLY},
		{195*8+3,155,SYS_AIRSUPPLY},
		{195*8+4,141,SYS_TRACTIONDRIVE},
		{195*8+5,142,SYS_TRACTIONDRIVE},
		{195*8+6,170,SYS_POWERSUPPLY},
		{195*8+7,586,SYS_AIRSUPPLY},
		{196*8+0,162,SYS_TRACTIONDRIVE},
		{196*8+1,163,SYS_TRACTIONDRIVE},
		{196*8+2,164,SYS_TRACTIONDRIVE},
		{196*8+3,165,SYS_TRACTIONDRIVE},
		{196*8+4,682,SYS_POWERSUPPLY},
		{200*8+2,108,SYS_TRAINDEVICE},
		{200*8+3,109,SYS_TRAINDEVICE},
		{200*8+4,110,SYS_TRAINDEVICE},
		{200*8+5,111,SYS_TRAINDEVICE}
	};

	//7号车终端
	const unsigned int m_E27uiNo07Terminal[][3] =
	{
		{210*8+0,2,SYS_TRACTIONDRIVE},
		{210*8+1,52,SYS_AIRSUPPLY},
		{210*8+2,59,SYS_AIRSUPPLY},
		{210*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{210*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{210*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{210*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{211*8+1,266,SYS_UNKNOW},
		{211*8+2,265,SYS_UNKNOW},
		{214*8+0,830,SYS_NETCONTROL},
		{214*8+1,831,SYS_NETCONTROL},
		{214*8+2,832,SYS_NETCONTROL},
		{214*8+3,833,SYS_NETCONTROL},
		{214*8+4,834,SYS_NETCONTROL},
		{214*8+5,835,SYS_NETCONTROL},
		{214*8+6,836,SYS_NETCONTROL},
		{214*8+7,837,SYS_NETCONTROL},
		{215*8+0,838,SYS_NETCONTROL},
		{215*8+1,839,SYS_NETCONTROL},
		{215*8+2,840,SYS_NETCONTROL},
		{215*8+3,841,SYS_NETCONTROL},
		{215*8+4,850,SYS_NETCONTROL},
		{215*8+5,851,SYS_NETCONTROL},
		{215*8+6,852,SYS_NETCONTROL},
		{215*8+7,853,SYS_NETCONTROL},
		{216*8+0,854,SYS_NETCONTROL},
		{216*8+1,855,SYS_NETCONTROL},
		{216*8+2,856,SYS_NETCONTROL},
		{216*8+3,857,SYS_NETCONTROL},
		{216*8+4,858,SYS_NETCONTROL},
		{216*8+5,859,SYS_NETCONTROL},
		{216*8+6,860,SYS_NETCONTROL},
		{216*8+7,861,SYS_NETCONTROL},
		{217*8+2,490,SYS_AIRSUPPLY},
		{217*8+3,491,SYS_AIRSUPPLY},
		{217*8+4,492,SYS_AIRSUPPLY},
		{217*8+5,493,SYS_AIRSUPPLY},
		{217*8+6,156,SYS_TRACTIONDRIVE},
		{217*8+7,80,SYS_UNKNOW},
		{218*8+0,123,SYS_AIRSUPPLY},
		{218*8+1,4,SYS_TRACTIONDRIVE},
		{218*8+2,5,SYS_TRACTIONDRIVE},
		{218*8+3,134,SYS_TRACTIONDRIVE},
		{218*8+4,137,SYS_TRACTIONDRIVE},
		{218*8+5,138,SYS_TRACTIONDRIVE},
		{218*8+6,139,SYS_TRACTIONDRIVE},
		{218*8+7,151,SYS_AIRSUPPLY},
		{219*8+0,152,SYS_AIRSUPPLY},
		{219*8+1,153,SYS_AIRSUPPLY},
		{219*8+2,154,SYS_AIRSUPPLY},
		{219*8+3,155,SYS_AIRSUPPLY},
		{219*8+4,141,SYS_TRACTIONDRIVE},
		{219*8+5,142,SYS_TRACTIONDRIVE},
		{219*8+7,586,SYS_AIRSUPPLY},
		{224*8+2,108,SYS_TRAINDEVICE},
		{224*8+3,109,SYS_TRAINDEVICE},
		{224*8+4,110,SYS_TRAINDEVICE},
		{224*8+5,111,SYS_TRAINDEVICE},
		{225*8+7,82,SYS_UNKNOW}

	};

	//8号车终端
	const unsigned int m_E27uiNo08Terminal[][3] =
	{
		{233*8+1,52,SYS_AIRSUPPLY},
		{233*8+2,59,SYS_AIRSUPPLY},
		{233*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{233*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{233*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{233*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{233*8+7,204,SYS_POWERSUPPLY},
		{240*8+0,830,SYS_NETCONTROL},
		{240*8+1,831,SYS_NETCONTROL},
		{240*8+2,832,SYS_NETCONTROL},
		{240*8+3,833,SYS_NETCONTROL},
		{240*8+4,834,SYS_NETCONTROL},
		{240*8+5,835,SYS_NETCONTROL},
		{240*8+6,836,SYS_NETCONTROL},
		{240*8+7,837,SYS_NETCONTROL},
		{241*8+0,838,SYS_NETCONTROL},
		{241*8+1,839,SYS_NETCONTROL},
		{241*8+2,840,SYS_NETCONTROL},
		{241*8+3,841,SYS_NETCONTROL},
		{241*8+4,850,SYS_NETCONTROL},
		{241*8+5,851,SYS_NETCONTROL},
		{241*8+6,852,SYS_NETCONTROL},
		{241*8+7,853,SYS_NETCONTROL},
		{242*8+0,854,SYS_NETCONTROL},
		{242*8+1,855,SYS_NETCONTROL},
		{242*8+2,856,SYS_NETCONTROL},
		{242*8+3,857,SYS_NETCONTROL},
		{242*8+4,858,SYS_NETCONTROL},
		{242*8+5,859,SYS_NETCONTROL},
		{242*8+6,860,SYS_NETCONTROL},
		{242*8+7,861,SYS_NETCONTROL},
		{245*8+0,123,SYS_AIRSUPPLY},
		{245*8+1,135,SYS_POWERSUPPLY},
		{245*8+7,151,SYS_AIRSUPPLY},
		{246*8+0,152,SYS_AIRSUPPLY},
		{246*8+1,153,SYS_AIRSUPPLY},
		{246*8+2,154,SYS_AIRSUPPLY},
		{246*8+3,155,SYS_AIRSUPPLY},
		{246*8+4,166,SYS_POWERSUPPLY},
		{246*8+5,143,SYS_POWERSUPPLY},
		{246*8+6,586,SYS_AIRSUPPLY},
		{247*8+3,490,SYS_AIRSUPPLY},
		{247*8+4,491,SYS_AIRSUPPLY},
		{247*8+5,492,SYS_AIRSUPPLY},
		{247*8+6,493,SYS_AIRSUPPLY},
		{247*8+7,82,SYS_UNKNOW},
		{248*8+4,83,SYS_UNKNOW},
		{248*8+5,266,SYS_UNKNOW},
		{248*8+6,265,SYS_UNKNOW}
	};

//////////////////////////////////////////////////////////////////////
	//E26故障代码

	//1号车中央1系
	const unsigned int m_E26uiNo01Center1[][3] =
	{
		{15*8+0,657,SYS_NETCONTROL},
		{15*8+1,661,SYS_NETCONTROL},
		{15*8+2,665,SYS_NETCONTROL},
		{15*8+3,666,SYS_NETCONTROL},
		{15*8+4,695,SYS_NETCONTROL},
		{15*8+5,911,SYS_UNKNOW},
		{15*8+6,194,SYS_HIGHPOWERSUPPLY},//高压供电系统
		{18*8+0,830,SYS_NETCONTROL},
		{18*8+1,831,SYS_NETCONTROL},
		{18*8+2,832,SYS_NETCONTROL},
		{18*8+3,833,SYS_NETCONTROL},
		{18*8+4,834,SYS_NETCONTROL},
		{18*8+5,835,SYS_NETCONTROL},
		{18*8+6,836,SYS_NETCONTROL},
		{18*8+7,837,SYS_NETCONTROL},
		{19*8+0,838,SYS_NETCONTROL},
		{19*8+1,839,SYS_NETCONTROL},
		{19*8+2,840,SYS_NETCONTROL},
		{19*8+3,841,SYS_NETCONTROL},
		{19*8+4,850,SYS_NETCONTROL},
		{19*8+5,851,SYS_NETCONTROL},
		{19*8+6,852,SYS_NETCONTROL},
		{19*8+7,853,SYS_NETCONTROL},
		{20*8+0,854,SYS_NETCONTROL},
		{20*8+1,855,SYS_NETCONTROL},
		{20*8+2,856,SYS_NETCONTROL},
		{20*8+3,857,SYS_NETCONTROL},
		{20*8+4,858,SYS_NETCONTROL},
		{20*8+5,859,SYS_NETCONTROL},
		{20*8+6,860,SYS_NETCONTROL},
		{20*8+7,861,SYS_NETCONTROL},
		{23*8+0,826,SYS_NETCONTROL}
	};

	//1号车中央2系
	const unsigned int m_E26uiNo01Center2[][3] =
	{
		{28*8+4,696,SYS_NETCONTROL},
		{30*8+0,830,SYS_NETCONTROL},
		{30*8+1,831,SYS_NETCONTROL},
		{30*8+2,832,SYS_NETCONTROL},
		{30*8+3,833,SYS_NETCONTROL},
		{30*8+4,834,SYS_NETCONTROL},
		{30*8+5,835,SYS_NETCONTROL},
		{30*8+6,836,SYS_NETCONTROL},
		{30*8+7,837,SYS_NETCONTROL},
		{31*8+0,838,SYS_NETCONTROL},
		{31*8+1,839,SYS_NETCONTROL},
		{31*8+2,840,SYS_NETCONTROL},
		{31*8+3,841,SYS_NETCONTROL},
		{31*8+4,850,SYS_NETCONTROL},
		{31*8+5,851,SYS_NETCONTROL},
		{31*8+6,852,SYS_NETCONTROL},
		{31*8+7,853,SYS_NETCONTROL},
		{32*8+0,854,SYS_NETCONTROL},
		{32*8+1,855,SYS_NETCONTROL},
		{32*8+2,856,SYS_NETCONTROL},
		{32*8+3,857,SYS_NETCONTROL},
		{32*8+4,858,SYS_NETCONTROL},
		{32*8+5,859,SYS_NETCONTROL},
		{32*8+6,860,SYS_NETCONTROL},
		{32*8+7,861,SYS_NETCONTROL}
	};

	//8号车中央1系
	const unsigned int m_E26uiNo08Center1[][3] =
	{
		{37*8+4,695,SYS_NETCONTROL},
		{37*8+6,194,SYS_HIGHPOWERSUPPLY},//高压供电系统
		{40*8+0,830,SYS_NETCONTROL},
		{40*8+1,831,SYS_NETCONTROL},
		{40*8+2,832,SYS_NETCONTROL},
		{40*8+3,833,SYS_NETCONTROL},
		{40*8+4,834,SYS_NETCONTROL},
		{40*8+5,835,SYS_NETCONTROL},
		{40*8+6,836,SYS_NETCONTROL},
		{40*8+7,837,SYS_NETCONTROL},
		{41*8+0,838,SYS_NETCONTROL},
		{41*8+1,839,SYS_NETCONTROL},
		{41*8+2,840,SYS_NETCONTROL},
		{41*8+3,841,SYS_NETCONTROL},
		{41*8+4,850,SYS_NETCONTROL},
		{41*8+5,851,SYS_NETCONTROL},
		{41*8+6,852,SYS_NETCONTROL},
		{41*8+7,853,SYS_NETCONTROL},
		{42*8+0,854,SYS_NETCONTROL},
		{42*8+1,855,SYS_NETCONTROL},
		{42*8+2,856,SYS_NETCONTROL},
		{42*8+3,857,SYS_NETCONTROL},
		{42*8+4,858,SYS_NETCONTROL},
		{42*8+5,859,SYS_NETCONTROL},
		{42*8+6,860,SYS_NETCONTROL},
		{42*8+7,861,SYS_NETCONTROL}
	};
	//8号车中央2系
	const unsigned int m_E26uiNo08Center2[][3] =
	{
		{50*8+4,696,SYS_NETCONTROL},
		{52*8+0,830,SYS_NETCONTROL},
		{52*8+1,831,SYS_NETCONTROL},
		{52*8+2,832,SYS_NETCONTROL},
		{52*8+3,833,SYS_NETCONTROL},
		{52*8+4,834,SYS_NETCONTROL},
		{52*8+5,835,SYS_NETCONTROL},
		{52*8+6,836,SYS_NETCONTROL},
		{52*8+7,837,SYS_NETCONTROL},
		{53*8+0,838,SYS_NETCONTROL},
		{53*8+1,839,SYS_NETCONTROL},
		{53*8+2,840,SYS_NETCONTROL},
		{53*8+3,841,SYS_NETCONTROL},
		{53*8+4,850,SYS_NETCONTROL},
		{53*8+5,851,SYS_NETCONTROL},
		{53*8+6,852,SYS_NETCONTROL},
		{53*8+7,853,SYS_NETCONTROL},
		{54*8+0,854,SYS_NETCONTROL},
		{54*8+1,855,SYS_NETCONTROL},
		{54*8+2,856,SYS_NETCONTROL},
		{54*8+3,857,SYS_NETCONTROL},
		{54*8+4,858,SYS_NETCONTROL},
		{54*8+5,859,SYS_NETCONTROL},
		{54*8+6,860,SYS_NETCONTROL},
		{54*8+7,861,SYS_NETCONTROL}
	};

	//1号车终端
	const unsigned int m_E26uiNo01Terminal[][3] =
	{
		{62*8+1,52,SYS_AIRSUPPLY},
		{62*8+2,59,SYS_AIRSUPPLY},
		{62*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{62*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{62*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{62*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{62*8+7,204,SYS_POWERSUPPLY},
		{69*8+0,830,SYS_NETCONTROL},
		{69*8+1,831,SYS_NETCONTROL},
		{69*8+2,832,SYS_NETCONTROL},
		{69*8+3,833,SYS_NETCONTROL},
		{69*8+4,834,SYS_NETCONTROL},
		{69*8+5,835,SYS_NETCONTROL},
		{69*8+6,836,SYS_NETCONTROL},
		{69*8+7,837,SYS_NETCONTROL},
		{70*8+0,838,SYS_NETCONTROL},
		{70*8+1,839,SYS_NETCONTROL},
		{70*8+2,840,SYS_NETCONTROL},
		{70*8+3,841,SYS_NETCONTROL},
		{70*8+4,850,SYS_NETCONTROL},
		{70*8+5,851,SYS_NETCONTROL},
		{70*8+6,852,SYS_NETCONTROL},
		{70*8+7,853,SYS_NETCONTROL},
		{71*8+0,854,SYS_NETCONTROL},
		{71*8+1,855,SYS_NETCONTROL},
		{71*8+2,856,SYS_NETCONTROL},
		{71*8+3,857,SYS_NETCONTROL},
		{71*8+4,858,SYS_NETCONTROL},
		{71*8+5,859,SYS_NETCONTROL},
		{71*8+6,860,SYS_NETCONTROL},
		{71*8+7,861,SYS_NETCONTROL},
		{74*8+0,123,SYS_AIRSUPPLY},
		{74*8+1,135,SYS_POWERSUPPLY},
		{74*8+7,151,SYS_AIRSUPPLY},
		{75*8+0,152,SYS_AIRSUPPLY},
		{75*8+1,153,SYS_AIRSUPPLY},
		{75*8+2,154,SYS_AIRSUPPLY},
		{75*8+3,155,SYS_AIRSUPPLY},
		{75*8+4,166,SYS_POWERSUPPLY},
		{75*8+5,143,SYS_POWERSUPPLY},
		{75*8+6,586,SYS_AIRSUPPLY},
		{76*8+3,490,SYS_AIRSUPPLY},
		{76*8+4,491,SYS_AIRSUPPLY},
		{76*8+5,492,SYS_AIRSUPPLY},
		{76*8+6,493,SYS_AIRSUPPLY},
		{76*8+7,82,SYS_UNKNOW},
		{77*8+4,83,SYS_UNKNOW},
		{77*8+5,266,SYS_UNKNOW},
		{77*8+6,265,SYS_UNKNOW},
		{82*8+2,108,SYS_TRAINDEVICE},
		{82*8+3,109,SYS_TRAINDEVICE}
	};

	//2号车终端
	const unsigned int m_E26uiNo02Terminal[][3] =
	{
		{92*8+0,2,SYS_TRACTIONDRIVE},
		{92*8+1,52,SYS_AIRSUPPLY},
		{92*8+2,59,SYS_AIRSUPPLY},
		{92*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{92*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{92*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{92*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{96*8+0,830,SYS_NETCONTROL},
		{96*8+1,831,SYS_NETCONTROL},
		{96*8+2,832,SYS_NETCONTROL},
		{96*8+3,833,SYS_NETCONTROL},
		{96*8+4,834,SYS_NETCONTROL},
		{96*8+5,835,SYS_NETCONTROL},
		{96*8+6,836,SYS_NETCONTROL},
		{96*8+7,837,SYS_NETCONTROL},
		{97*8+0,838,SYS_NETCONTROL},
		{97*8+1,839,SYS_NETCONTROL},
		{97*8+2,840,SYS_NETCONTROL},
		{97*8+3,841,SYS_NETCONTROL},
		{97*8+4,850,SYS_NETCONTROL},
		{97*8+5,851,SYS_NETCONTROL},
		{97*8+6,852,SYS_NETCONTROL},
		{97*8+7,853,SYS_NETCONTROL},
		{98*8+0,854,SYS_NETCONTROL},
		{98*8+1,855,SYS_NETCONTROL},
		{98*8+2,856,SYS_NETCONTROL},
		{98*8+3,857,SYS_NETCONTROL},
		{98*8+4,858,SYS_NETCONTROL},
		{98*8+5,859,SYS_NETCONTROL},
		{98*8+6,860,SYS_NETCONTROL},
		{98*8+7,861,SYS_NETCONTROL},
		{99*8+2,490,SYS_AIRSUPPLY},
		{99*8+3,491,SYS_AIRSUPPLY},
		{99*8+4,492,SYS_AIRSUPPLY},
		{99*8+5,493,SYS_AIRSUPPLY},
		{99*8+6,156,SYS_TRACTIONDRIVE},
		{100*8+0,123,SYS_AIRSUPPLY},
		{100*8+1,4,SYS_TRACTIONDRIVE},
		{100*8+2,5,SYS_TRACTIONDRIVE},
		{100*8+3,134,SYS_TRACTIONDRIVE},
		{100*8+4,137,SYS_TRACTIONDRIVE},
		{100*8+5,138,SYS_TRACTIONDRIVE},
		{100*8+6,139,SYS_TRACTIONDRIVE},
		{100*8+7,151,SYS_AIRSUPPLY},
		{101*8+0,152,SYS_AIRSUPPLY},
		{101*8+1,153,SYS_AIRSUPPLY},
		{101*8+2,154,SYS_AIRSUPPLY},
		{101*8+3,155,SYS_AIRSUPPLY},
		{101*8+4,141,SYS_TRACTIONDRIVE},
		{101*8+5,142,SYS_TRACTIONDRIVE},
		{101*8+6,170,SYS_POWERSUPPLY},
		{101*8+7,586,SYS_AIRSUPPLY},
		{102*8+0,162,SYS_TRACTIONDRIVE},
		{102*8+1,163,SYS_TRACTIONDRIVE},
		{102*8+2,164,SYS_TRACTIONDRIVE},
		{102*8+3,165,SYS_TRACTIONDRIVE},
		{102*8+5,266,SYS_UNKNOW},
		{102*8+6,265,SYS_UNKNOW},
		{106*8+2,108,SYS_TRAINDEVICE},
		{106*8+3,109,SYS_TRAINDEVICE},
		{106*8+4,110,SYS_TRAINDEVICE},
		{106*8+5,111,SYS_TRAINDEVICE}
	};

	//3号车终端
	const unsigned int m_E26uiNo03Terminal[][3] =
	{
		{116*8+0,2,SYS_TRACTIONDRIVE},
		{116*8+1,52,SYS_AIRSUPPLY},
		{116*8+2,59,SYS_AIRSUPPLY},
		{116*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{116*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{116*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{116*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{120*8+0,830,SYS_NETCONTROL},
		{120*8+1,831,SYS_NETCONTROL},
		{120*8+2,832,SYS_NETCONTROL},
		{120*8+3,833,SYS_NETCONTROL},
		{120*8+4,834,SYS_NETCONTROL},
		{120*8+5,835,SYS_NETCONTROL},
		{120*8+6,836,SYS_NETCONTROL},
		{120*8+7,837,SYS_NETCONTROL},
		{121*8+0,838,SYS_NETCONTROL},
		{121*8+1,839,SYS_NETCONTROL},
		{121*8+2,840,SYS_NETCONTROL},
		{121*8+3,841,SYS_NETCONTROL},
		{121*8+4,850,SYS_NETCONTROL},
		{121*8+5,851,SYS_NETCONTROL},
		{121*8+6,852,SYS_NETCONTROL},
		{121*8+7,853,SYS_NETCONTROL},
		{122*8+0,854,SYS_NETCONTROL},
		{122*8+1,855,SYS_NETCONTROL},
		{122*8+2,856,SYS_NETCONTROL},
		{122*8+3,857,SYS_NETCONTROL},
		{122*8+4,858,SYS_NETCONTROL},
		{122*8+5,859,SYS_NETCONTROL},
		{122*8+6,860,SYS_NETCONTROL},
		{122*8+7,861,SYS_NETCONTROL},
		{123*8+2,490,SYS_AIRSUPPLY},
		{123*8+3,491,SYS_AIRSUPPLY},
		{123*8+4,492,SYS_AIRSUPPLY},
		{123*8+5,493,SYS_AIRSUPPLY},
		{123*8+6,156,SYS_TRACTIONDRIVE},
		{124*8+0,123,SYS_AIRSUPPLY},
		{124*8+1,4,SYS_TRACTIONDRIVE},
		{124*8+2,5,SYS_TRACTIONDRIVE},
		{124*8+3,134,SYS_TRACTIONDRIVE},
		{124*8+4,137,SYS_TRACTIONDRIVE},
		{124*8+5,138,SYS_TRACTIONDRIVE},
		{124*8+6,139,SYS_TRACTIONDRIVE},
		{124*8+7,151,SYS_AIRSUPPLY},
		{125*8+0,152,SYS_AIRSUPPLY},
		{125*8+1,153,SYS_AIRSUPPLY},
		{125*8+2,154,SYS_AIRSUPPLY},
		{125*8+3,155,SYS_AIRSUPPLY},
		{125*8+4,141,SYS_TRACTIONDRIVE},
		{125*8+5,142,SYS_TRACTIONDRIVE},
		{125*8+7,586,SYS_AIRSUPPLY},
		{126*8+5,266,SYS_UNKNOW},
		{126*8+6,265,SYS_UNKNOW},
		{130*8+2,108,SYS_TRAINDEVICE},
		{130*8+3,109,SYS_TRAINDEVICE},
		{130*8+4,110,SYS_TRAINDEVICE},
		{130*8+5,111,SYS_TRAINDEVICE}
	};

	//4号车终端
	const unsigned int m_E26uiNo04Terminal[][3] =
	{
		{139*8+1,52,SYS_AIRSUPPLY},
		{139*8+2,59,SYS_AIRSUPPLY},
		{139*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{139*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{139*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{139*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{146*8+0,830,SYS_NETCONTROL},
		{146*8+1,831,SYS_NETCONTROL},
		{146*8+2,832,SYS_NETCONTROL},
		{146*8+3,833,SYS_NETCONTROL},
		{146*8+4,834,SYS_NETCONTROL},
		{146*8+5,835,SYS_NETCONTROL},
		{146*8+6,836,SYS_NETCONTROL},
		{146*8+7,837,SYS_NETCONTROL},
		{147*8+0,838,SYS_NETCONTROL},
		{147*8+1,839,SYS_NETCONTROL},
		{147*8+2,840,SYS_NETCONTROL},
		{147*8+3,841,SYS_NETCONTROL},
		{147*8+4,850,SYS_NETCONTROL},
		{147*8+5,851,SYS_NETCONTROL},
		{147*8+6,852,SYS_NETCONTROL},
		{147*8+7,853,SYS_NETCONTROL},
		{148*8+0,854,SYS_NETCONTROL},
		{148*8+1,855,SYS_NETCONTROL},
		{148*8+2,856,SYS_NETCONTROL},
		{148*8+3,857,SYS_NETCONTROL},
		{148*8+4,858,SYS_NETCONTROL},
		{148*8+5,859,SYS_NETCONTROL},
		{148*8+6,860,SYS_NETCONTROL},
		{148*8+7,861,SYS_NETCONTROL},
		{150*8+2,490,SYS_AIRSUPPLY},
		{150*8+3,491,SYS_AIRSUPPLY},
		{150*8+4,492,SYS_AIRSUPPLY},
		{150*8+5,493,SYS_AIRSUPPLY},
		{150*8+6,156,SYS_TRACTIONDRIVE},
		{150*8+7,82,SYS_UNKNOW},
		{151*8+0,123,SYS_AIRSUPPLY},
		{151*8+6,586,SYS_AIRSUPPLY},
		{151*8+7,151,SYS_AIRSUPPLY},
		{152*8+0,152,SYS_AIRSUPPLY},
		{152*8+1,153,SYS_AIRSUPPLY},
		{152*8+2,154,SYS_AIRSUPPLY},
		{152*8+3,155,SYS_AIRSUPPLY},
		{152*8+4,682,SYS_POWERSUPPLY},
		{152*8+5,266,SYS_UNKNOW},
		{152*8+6,265,SYS_UNKNOW},
		{159*8+2,108,SYS_TRAINDEVICE},
		{159*8+3,109,SYS_TRAINDEVICE},
		{159*8+4,110,SYS_TRAINDEVICE},
		{159*8+5,111,SYS_TRAINDEVICE}
	};

	//5号车终端
	const unsigned int m_E26uiNo05Terminal[][3] =
	{
		{169*8+1,52,SYS_AIRSUPPLY},
		{169*8+2,59,SYS_AIRSUPPLY},
		{169*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{169*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{169*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{169*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{176*8+0,830,SYS_NETCONTROL},
		{176*8+1,831,SYS_NETCONTROL},
		{176*8+2,832,SYS_NETCONTROL},
		{176*8+3,833,SYS_NETCONTROL},
		{176*8+4,834,SYS_NETCONTROL},
		{176*8+5,835,SYS_NETCONTROL},
		{176*8+6,836,SYS_NETCONTROL},
		{176*8+7,837,SYS_NETCONTROL},
		{177*8+0,838,SYS_NETCONTROL},
		{177*8+1,839,SYS_NETCONTROL},
		{177*8+2,840,SYS_NETCONTROL},
		{177*8+3,841,SYS_NETCONTROL},
		{177*8+4,850,SYS_NETCONTROL},
		{177*8+5,851,SYS_NETCONTROL},
		{177*8+6,852,SYS_NETCONTROL},
		{177*8+7,853,SYS_NETCONTROL},
		{178*8+0,854,SYS_NETCONTROL},
		{178*8+1,855,SYS_NETCONTROL},
		{178*8+2,856,SYS_NETCONTROL},
		{178*8+3,857,SYS_NETCONTROL},
		{178*8+4,858,SYS_NETCONTROL},
		{178*8+5,859,SYS_NETCONTROL},
		{178*8+6,860,SYS_NETCONTROL},
		{178*8+7,861,SYS_NETCONTROL},
		{180*8+2,490,SYS_AIRSUPPLY},
		{180*8+3,491,SYS_AIRSUPPLY},
		{180*8+4,492,SYS_AIRSUPPLY},
		{180*8+5,493,SYS_AIRSUPPLY},
		{180*8+6,156,SYS_TRACTIONDRIVE},
		{180*8+7,82,SYS_UNKNOW},
		{181*8+0,123,SYS_AIRSUPPLY},
		{181*8+6,586,SYS_AIRSUPPLY},
		{181*8+7,151,SYS_AIRSUPPLY},
		{182*8+0,152,SYS_AIRSUPPLY},
		{182*8+1,153,SYS_AIRSUPPLY},
		{182*8+2,154,SYS_AIRSUPPLY},
		{182*8+3,155,SYS_AIRSUPPLY},
		{182*8+5,266,SYS_UNKNOW},
		{182*8+6,265,SYS_UNKNOW},
		{189*8+2,108,SYS_TRAINDEVICE},
		{189*8+3,109,SYS_TRAINDEVICE}

	};

	//6号车终端
	const unsigned int m_E26uiNo06Terminal[][3] =
	{
		{199*8+0,2,SYS_TRACTIONDRIVE},
		{199*8+1,52,SYS_AIRSUPPLY},
		{199*8+2,59,SYS_AIRSUPPLY},
		{199*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{199*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{199*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{199*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{203*8+0,830,SYS_NETCONTROL},
		{203*8+1,831,SYS_NETCONTROL},
		{203*8+2,832,SYS_NETCONTROL},
		{203*8+3,833,SYS_NETCONTROL},
		{203*8+4,834,SYS_NETCONTROL},
		{203*8+5,835,SYS_NETCONTROL},
		{203*8+6,836,SYS_NETCONTROL},
		{203*8+7,837,SYS_NETCONTROL},
		{204*8+0,838,SYS_NETCONTROL},
		{204*8+1,839,SYS_NETCONTROL},
		{204*8+2,840,SYS_NETCONTROL},
		{204*8+3,841,SYS_NETCONTROL},
		{204*8+4,850,SYS_NETCONTROL},
		{204*8+5,851,SYS_NETCONTROL},
		{204*8+6,852,SYS_NETCONTROL},
		{204*8+7,853,SYS_NETCONTROL},
		{205*8+0,854,SYS_NETCONTROL},
		{205*8+1,855,SYS_NETCONTROL},
		{205*8+2,856,SYS_NETCONTROL},
		{205*8+3,857,SYS_NETCONTROL},
		{205*8+4,858,SYS_NETCONTROL},
		{205*8+5,859,SYS_NETCONTROL},
		{205*8+6,860,SYS_NETCONTROL},
		{205*8+7,861,SYS_NETCONTROL},
		{206*8+2,490,SYS_AIRSUPPLY},
		{206*8+3,491,SYS_AIRSUPPLY},
		{206*8+4,492,SYS_AIRSUPPLY},
		{206*8+5,493,SYS_AIRSUPPLY},
		{206*8+6,156,SYS_TRACTIONDRIVE},
		{207*8+0,123,SYS_AIRSUPPLY},
		{207*8+1,4,SYS_TRACTIONDRIVE},
		{207*8+2,5,SYS_TRACTIONDRIVE},
		{207*8+3,134,SYS_TRACTIONDRIVE},
		{207*8+4,137,SYS_TRACTIONDRIVE},
		{207*8+5,138,SYS_TRACTIONDRIVE},
		{207*8+6,139,SYS_TRACTIONDRIVE},
		{207*8+7,151,SYS_AIRSUPPLY},
		{208*8+0,152,SYS_AIRSUPPLY},
		{208*8+1,153,SYS_AIRSUPPLY},
		{208*8+2,154,SYS_AIRSUPPLY},
		{208*8+3,155,SYS_AIRSUPPLY},
		{208*8+4,141,SYS_TRACTIONDRIVE},
		{208*8+5,142,SYS_TRACTIONDRIVE},
		{208*8+6,170,SYS_POWERSUPPLY},
		{208*8+7,586,SYS_AIRSUPPLY},
		{209*8+0,162,SYS_TRACTIONDRIVE},
		{209*8+1,163,SYS_TRACTIONDRIVE},
		{209*8+2,164,SYS_TRACTIONDRIVE},
		{209*8+3,165,SYS_TRACTIONDRIVE},
		{209*8+4,682,SYS_POWERSUPPLY},
		{209*8+5,266,SYS_UNKNOW},
		{209*8+6,265,SYS_UNKNOW}
	};

	//7号车终端
	const unsigned int m_E26uiNo07Terminal[][3] =
	{
		{223*8+0,2,SYS_TRACTIONDRIVE},
		{223*8+1,52,SYS_AIRSUPPLY},
		{223*8+2,59,SYS_AIRSUPPLY},
		{223*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{223*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{223*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{223*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{227*8+0,830,SYS_NETCONTROL},
		{227*8+1,831,SYS_NETCONTROL},
		{227*8+2,832,SYS_NETCONTROL},
		{227*8+3,833,SYS_NETCONTROL},
		{227*8+4,834,SYS_NETCONTROL},
		{227*8+5,835,SYS_NETCONTROL},
		{227*8+6,836,SYS_NETCONTROL},
		{227*8+7,837,SYS_NETCONTROL},
		{228*8+0,838,SYS_NETCONTROL},
		{228*8+1,839,SYS_NETCONTROL},
		{228*8+2,840,SYS_NETCONTROL},
		{228*8+3,841,SYS_NETCONTROL},
		{228*8+4,850,SYS_NETCONTROL},
		{228*8+5,851,SYS_NETCONTROL},
		{228*8+6,852,SYS_NETCONTROL},
		{228*8+7,853,SYS_NETCONTROL},
		{229*8+0,854,SYS_NETCONTROL},
		{229*8+1,855,SYS_NETCONTROL},
		{229*8+2,856,SYS_NETCONTROL},
		{229*8+3,857,SYS_NETCONTROL},
		{229*8+4,858,SYS_NETCONTROL},
		{229*8+5,859,SYS_NETCONTROL},
		{229*8+6,860,SYS_NETCONTROL},
		{229*8+7,861,SYS_NETCONTROL},
		{230*8+2,490,SYS_AIRSUPPLY},
		{230*8+3,491,SYS_AIRSUPPLY},
		{230*8+4,492,SYS_AIRSUPPLY},
		{230*8+5,493,SYS_AIRSUPPLY},
		{230*8+6,456,SYS_UNKNOW},
		{231*8+0,123,SYS_AIRSUPPLY},
		{231*8+1,4,SYS_TRACTIONDRIVE},
		{231*8+2,5,SYS_TRACTIONDRIVE},
		{231*8+3,134,SYS_TRACTIONDRIVE},
		{231*8+4,137,SYS_TRACTIONDRIVE},
		{231*8+5,138,SYS_TRACTIONDRIVE},
		{231*8+6,139,SYS_TRACTIONDRIVE},
		{231*8+7,151,SYS_AIRSUPPLY},
		{232*8+0,152,SYS_AIRSUPPLY},
		{232*8+1,153,SYS_AIRSUPPLY},
		{232*8+2,154,SYS_AIRSUPPLY},
		{232*8+3,155,SYS_AIRSUPPLY},
		{232*8+4,141,SYS_TRACTIONDRIVE},
		{232*8+5,142,SYS_TRACTIONDRIVE},
		{232*8+7,586,SYS_AIRSUPPLY},
		{233*8+5,266,SYS_UNKNOW},
		{233*8+6,265,SYS_UNKNOW},
		{237*8+2,108,SYS_TRAINDEVICE},
		{237*8+3,109,SYS_TRAINDEVICE},
		{237*8+4,110,SYS_TRAINDEVICE},
		{237*8+5,111,SYS_TRAINDEVICE}
	};

	//8号车终端
	const unsigned int m_E26uiNo08Terminal[][3] =
	{
		{246*8+1,52,SYS_AIRSUPPLY},
		{246*8+2,59,SYS_AIRSUPPLY},
		{246*8+3,60,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{246*8+4,61,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{246*8+5,62,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{246*8+6,63,SYS_TRACTIONDRIVE|SYS_AIRSUPPLY},
		{246*8+7,204,SYS_POWERSUPPLY},
		{253*8+0,830,SYS_NETCONTROL},
		{253*8+1,831,SYS_NETCONTROL},
		{253*8+2,832,SYS_NETCONTROL},
		{253*8+3,833,SYS_NETCONTROL},
		{253*8+4,834,SYS_NETCONTROL},
		{253*8+5,835,SYS_NETCONTROL},
		{253*8+6,836,SYS_NETCONTROL},
		{253*8+7,837,SYS_NETCONTROL},
		{254*8+0,838,SYS_NETCONTROL},
		{254*8+1,839,SYS_NETCONTROL},
		{254*8+2,840,SYS_NETCONTROL},
		{254*8+3,841,SYS_NETCONTROL},
		{254*8+4,850,SYS_NETCONTROL},
		{254*8+5,851,SYS_NETCONTROL},
		{254*8+6,852,SYS_NETCONTROL},
		{254*8+7,853,SYS_NETCONTROL},
		{255*8+0,854,SYS_NETCONTROL},
		{255*8+1,855,SYS_NETCONTROL},
		{255*8+2,856,SYS_NETCONTROL},
		{255*8+3,857,SYS_NETCONTROL},
		{255*8+4,858,SYS_NETCONTROL},
		{255*8+5,859,SYS_NETCONTROL},
		{255*8+6,860,SYS_NETCONTROL},
		{255*8+7,861,SYS_NETCONTROL},
		{258*8+0,123,SYS_AIRSUPPLY},
		{258*8+1,135,SYS_POWERSUPPLY},
		{258*8+7,151,SYS_AIRSUPPLY},
		{259*8+0,152,SYS_AIRSUPPLY},
		{259*8+1,153,SYS_AIRSUPPLY},
		{259*8+2,154,SYS_AIRSUPPLY},
		{259*8+3,155,SYS_AIRSUPPLY},
		{259*8+4,166,SYS_POWERSUPPLY},
		{259*8+5,143,SYS_POWERSUPPLY},
		{259*8+6,586,SYS_AIRSUPPLY},
		{260*8+3,490,SYS_AIRSUPPLY},
		{260*8+4,491,SYS_AIRSUPPLY},
		{260*8+5,492,SYS_AIRSUPPLY},
		{260*8+6,493,SYS_AIRSUPPLY},
		{260*8+7,82,SYS_UNKNOW},
		{261*8+4,83,SYS_UNKNOW},
		{261*8+5,266,SYS_UNKNOW},
		{261*8+6,265,SYS_UNKNOW}
	};
		
}

#endif