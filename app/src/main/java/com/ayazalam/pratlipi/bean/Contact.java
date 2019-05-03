package com.ayazalam.pratlipi.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public class Contact implements Comparable<Contact>, Parcelable {

    private String id="not Available";
    private String name="Not Available";
    private String avatarUrl="Not Available";
    private String phoneNumber="Not Available";
    private String email="Not Available";
    private String nickName="Not Available";
    private String address="Not Available";
    private String pinyin="Not Available";
    private char firstChar='A';


    public Contact(String id, String name, String phoneNumber) {
        if(id!=null)
        this.id = id;
        if(name!=null)
        this.name = name;
        if(phoneNumber!=null)
        this.phoneNumber = phoneNumber;
    }


    public Contact() {

    }

    public Contact(String id){
        this.id = id;
    }


    protected Contact(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        nickName = in.readString();
        address = in.readString();
        pinyin = in.readString();
    }


    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }


        @Override public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };


    public String getId() {
        return id;
    }


    public void setId(String id) {
        if(id!=null)this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        if(name!=null)
        this.name = name;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }


    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {

        if(phoneNumber!=null)this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {

        if(email!=null)this.email = email;
    }


    public String getNickName() {
        return nickName;
    }


    public void setNickName(String nickName) {
        if(nickName!=null)
        this.nickName = nickName;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        if(address!=null)
        this.address = address;
    }


    public String getPinyin() {
        return pinyin;
    }


    public void setPinyin(String pinyin) {
        if(pinyin!=null)this.pinyin = pinyin;

    }


    public char getFirstChar() {
        return Character.toUpperCase(firstChar);
    }


    public void setFirstChar(char firstChar) {


        this.firstChar = Character.toUpperCase(firstChar);
    }


    @Override public int compareTo(Contact another) {
        if(pinyin!=null||another.getPinyin()!=null)
        return this.pinyin.compareTo(another.getPinyin());
        else return 0;
    }


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (firstChar != contact.firstChar) return false;
        if (!id.equals(contact.id)) return false;
        if (!name.equals(contact.name)) return false;
        if (avatarUrl != null
            ? !avatarUrl.equals(contact.avatarUrl)
            : contact.avatarUrl != null) {
            return false;
        }
        if (phoneNumber != null
            ? !phoneNumber.equals(contact.phoneNumber)
            : contact.phoneNumber != null) {
            return false;
        }
        if (email != null
            ? !email.equals(contact.email)
            : contact.email != null) {
            return false;
        }
        if (nickName != null
            ? !nickName.equals(contact.nickName)
            : contact.nickName != null) {
            return false;
        }
        if (address != null
            ? !address.equals(contact.address)
            : contact.address != null) {
            return false;
        }
        return pinyin != null
               ? pinyin.equals(contact.pinyin)
               : contact.pinyin == null;
    }


    @Override public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result +
                (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (pinyin != null ? pinyin.hashCode() : 0);
        result = 31 * result + (int) firstChar;
        return result;
    }


    @Override public int describeContents() {
        return 0;
    }


    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(nickName);
        dest.writeString(address);
        dest.writeString(pinyin);
    }

    Uri photo;
    public void setAvatarUrl(Uri photo) {
        this.photo = photo;
    }

    public Uri getPhoto() {
        return photo;
    }
}
