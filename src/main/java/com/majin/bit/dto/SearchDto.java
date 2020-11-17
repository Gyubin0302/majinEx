package com.majin.bit.dto;

import java.util.Arrays;

public class SearchDto {
	private String[] attrList;
	private String[] areaList;
	private String attract;
	private String area;
//	private String searchType;
//	private String searchWord;

	public String[] getAttrList() {
		return attrList;
	}

	public void setAttrList(String[] attrList) {
		this.attrList = attrList;
	}

	public String[] getAreaList() {
		return areaList;
	}

	public void setAreaList(String[] areaList) {
		this.areaList = areaList;
	}

	public String getAttract() {
		return attract;
	}

	public void setAttract(String attract) {
		this.attract = attract;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

//	public String getSearchType() {
//		return searchType;
//	}
//
//	public void setSearchType(String searchType) {
//		this.searchType = searchType;
//	}
//
//	public String getSearchWord() {
//		return searchWord;
//	}
//
//	public void setSearchWord(String searchWord) {
//		this.searchWord = searchWord;
//	}

	@Override
	public String toString() {
		return "BoadrSearch [attrList=" + Arrays.toString(attrList) + ", areaList=" + Arrays.toString(areaList)
				+ ", attract=" + attract + ", area=" + area + "]";
	}

}
