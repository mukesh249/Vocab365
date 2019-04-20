package com.example.advosoft.vocab365.wrapper;

import java.io.Serializable;

/**
 * Created by Advosoft2 on 4/13/2017.
 */

public class EndRoute implements Serializable {

    private static final long serialVersionUID = 155555L;
    private String startAddress = "";
    private String address = "";
    private double lat = 0.0;
    private double lng = 0.0;
    private long createdDate = 0;
    private long updatedDate = 0;
    private boolean is_completed = false;
    //private int noteId = 0;

    private String distance = "";
    private String title = "";
    private int    is_fav = 0;
    private int   imgCount = 0;
    private int   rowId = 0;
    private int   rowIdOfStartRoute = 0;
    //News
    private boolean haveImgNote = false;
    private boolean haveTextNote = false;
    private int routeType = -1;

    private int pinNumber=-1;
    private String src_address="";
    private double src_lat=0.0;
    private double src_lng=0.0;


    //Notes table variable
	/*private String noteOf = "";
	private int noteOfId = 0;
	private String noteText = "";
	private String imagUrl = "";*/

    /*public String getNoteOf() {
        return noteOf;
    }

    public void setNoteOf(String noteOf) {
        this.noteOf = noteOf;
    }

    public int getNoteOfId() {
        return noteOfId;
    }

    public void setNoteOfId(int noteOfId) {
        this.noteOfId = noteOfId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }*/
    public int getRowIdOfStartRoute() {
        return rowIdOfStartRoute;
    }

    public void setRowIdOfStartRoute(int rowIdOfStartRoute) {
        this.rowIdOfStartRoute = rowIdOfStartRoute;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int is_fav() {
        return is_fav;
    }

    public void set_fav(int is_fav) {
        this.is_fav = is_fav;
    }

    public int getImgCount() {
        return imgCount;
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getSrc_address() {
        return src_address;
    }

    public void setSrc_address(String src_address) {
        this.src_address = src_address;
    }

    public double getSrc_lat() {
        return src_lat;
    }

    public void setSrc_lat(double src_lat) {
        this.src_lat = src_lat;
    }

    public double getSrc_lng() {
        return src_lng;
    }

    public void setSrc_lng(double src_lng) {
        this.src_lng = src_lng;
    }

    public boolean isIs_completed() {
        return is_completed;
    }



    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

	/*public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}*/

    public boolean is_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "EndRoute [startAddress=" + startAddress + ", address="
                + address + ", lat=" + lat + ", lng=" + lng + ", createdDate="
                + createdDate + ", is_completed=" + is_completed + ", noteId="
                + ", updatedDate=" + updatedDate + ", distance="
                + distance + ", title=" + title + ", is_fav="
                + ", imgCount=" + imgCount + ", rowId=" + rowId
                + ", rowIdOfStartRoute=" + rowIdOfStartRoute + ", notesArray="
                + ", pinNumber=" + pinNumber
                + ", src_address=" + src_address + ", src_lat=" + src_lat
                + ", src_lng=" + src_lng + "]";
    }

    public boolean isHaveImgNote() {
        return haveImgNote;
    }

    public void setHaveImgNote(boolean haveImgNote) {
        this.haveImgNote = haveImgNote;
    }

    public boolean isHaveTextNote() {
        return haveTextNote;
    }

    public void setHaveTextNote(boolean haveTextNote) {
        this.haveTextNote = haveTextNote;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }
}
