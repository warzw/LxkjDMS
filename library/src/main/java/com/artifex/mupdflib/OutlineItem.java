package com.artifex.mupdflib;

public class OutlineItem {
	public  int level;
	public  String title;
	public  int page;

	OutlineItem(int _level, String _title, int _page) {
		level = _level;
		title = _title;
		page = _page;
	}

}
