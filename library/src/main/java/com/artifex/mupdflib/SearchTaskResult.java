package com.artifex.mupdflib;

import android.graphics.RectF;

import java.util.List;

public class SearchTaskResult {
	public  String txt;
	public  int pageNumber;
	public List<RectF[]> searchBoxesPrims;
	public List<RectF[]> searchBoxes;
	public static  SearchTaskResult singleton;

	SearchTaskResult(String _txt, int _pageNumber, List<RectF[]> _searchBoxes, List<RectF[]> _searchBoxesPrims) {
		txt = _txt;
		pageNumber = _pageNumber;
		searchBoxes = _searchBoxes;
		searchBoxesPrims = _searchBoxesPrims;
	}

	static public SearchTaskResult get() {
		return singleton;
	}

	static public void set(SearchTaskResult r) {
		singleton = r;
	}
}
