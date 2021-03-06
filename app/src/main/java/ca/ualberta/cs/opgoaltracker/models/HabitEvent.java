/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;

/**
 * This Habit Event describe a single Habit Event which belongs to a Habit Type<br>
 *     This Class allow user to get and set information in this object
 * @author Donglin Han, Yongjia Huang, Mingwei Li, Long Ma
 * @version 3.0
 * @see Parcelable
 * @since 1.0
 */
public class HabitEvent implements Parcelable {

    private String id;
    private String habitType;
    private String comment;
    private Date date;
    private Photograph photo;
    private String lat;
    private String lng;

    /**
     * Base Constructor which create a Habit Event type <br>
     *     the New Habit Event type has a restrict commetn length smaller than 20<br>
     * @param habitType
     * @param comment
     * @param date
     * @param commentSize
     * @throws CommentTooLongException
     */
    public HabitEvent(String habitType, String comment, Date date, int commentSize) throws CommentTooLongException {
        if(comment.length() > commentSize) {
            throw new CommentTooLongException();
        }

        this.id = java.util.UUID.randomUUID().toString();
        this.habitType = habitType;
        this.date = date;
        this.comment = comment;
        this.lat=null;
        this.lng=null;
    }

    /**
     * See if the new event created by editing habitevent changed anything
     * image is handled elsewhere, checks if location and comment stayed the same
     * @param b : Habitevent b the current habit event is compared to
     */
    public Boolean changed(HabitEvent b){
        if (b.getComment()==this.comment &&
                b.getLocation().get(0).equals(lat)&&
                b.getLocation().get(1).equals(lng)){
            return Boolean.FALSE;
        }else if(b.getComment()==this.comment &&
                b.getLocation().get(0)==null &&
                lat==null) {
            return Boolean.FALSE;
        }else {
            return Boolean.TRUE;
        }
    }

    /**
     * Set id for the HabitEvent object
     * @return id : String
     */
    public String getId() {
        return id;
    }

    /**
     * Get id of the HabitEvent object
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Basic Habit Type Getter
     * @return String : habitType
     */
    public String getHabitType() {
        return habitType;
    }

    /**
     * Basic Habit event comment getter
     * @return String : Comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Basic Habit Event Date getter
     * @return Date : date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Basic Habit Event Photo getter
     * @return Photograph : photo
     */
    public Photograph getPhoto() {
        return photo;
    }

    /**
     * Basic Habit Event Location getter
     * @return String : location
     */
    public ArrayList<String> getLocation() {
        ArrayList<String> r=new ArrayList<String>();
        r.add(lat);
        r.add(lng);

        return r;
    }

    /**
     * Basic Habit Event HabitType setter
     * @param habitType : String
     */
    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }

    /**
     *  Basic Habit Event Comment Setter
     * @param comment String
     * @param commentSize String
     * @throws CommentTooLongException
     */
    public void setComment(String comment, int commentSize) throws CommentTooLongException {
        if(comment.length() > commentSize) {
            throw new CommentTooLongException();
        }
        this.comment = comment;
    }

    /**
     *  Basic Habit Event Date Setter
     * @param date : Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *  Basic Habit Event Photo setter
     * @param photo Photograph
     */
    public void setPhoto(Photograph photo) {
        this.photo = photo;
    }

    /**
     *  Basic Habit Event Location setter
     *  @param lat: Latitude stored in a string format
     *  @param lng: longitude stored in a string format
     */
    public void setLocation(String lat,String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     *  String representation of the event, type followed by comment
     *  @return String: String
     */
    @Override
    public String toString(){
        return habitType+" : "+comment;
    }

    /**
     * implements parcelable
     * get in the object from parcel
     * @param in : parcel data
     */
    protected HabitEvent(Parcel in) {
        id = in.readString();
        habitType = in.readString();
        comment = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        lat = in.readString();
        lng = in.readString();
        int hasPhoto = in.readInt();
        if (hasPhoto==1){
            photo = in.readParcelable(Photograph.class.getClassLoader());
        }

    }

    /**
     * to override parcelable class
     * @return : 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * implements parcelable
     * writes the object to parcel
     * @param dest: parcel
     * @param flags: flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(habitType);
        dest.writeString(comment);
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeString(lat);
        dest.writeString(lng);
        if (photo==null){
            dest.writeInt(0);
        }else{
            dest.writeInt(1);
            dest.writeParcelable((Parcelable)photo,flags);
        }

    }

    /**
     * implements parcelable array
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HabitEvent> CREATOR = new Parcelable.Creator<HabitEvent>() {

        /**
         * implements hebitevent object
         * @param in: parcel
         * @return habitevent(i): retuns the habit event object
         * */
        @Override
        public HabitEvent createFromParcel(Parcel in) {
            return new HabitEvent(in);
        }

        /**
         * implements parcelable
         * get size of habitevent
         * @return size of habitevent;
         */
        @Override
        public HabitEvent[] newArray(int size) {
            return new HabitEvent[size];
        }
    };
}