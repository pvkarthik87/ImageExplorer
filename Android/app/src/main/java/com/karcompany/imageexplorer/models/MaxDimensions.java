/**
 * Created by pvkarthik on 2017-01-12.
 *
 * This is POJO class corresponding to server response (JSON).
 */
package com.karcompany.imageexplorer.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MaxDimensions implements Parcelable
{

    @SerializedName("height")
    @Expose
    private Long height;
    @SerializedName("width")
    @Expose
    private Long width;
    public final static Creator<MaxDimensions> CREATOR = new Creator<MaxDimensions>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MaxDimensions createFromParcel(Parcel in) {
            MaxDimensions instance = new MaxDimensions();
            instance.height = ((Long) in.readValue((Long.class.getClassLoader())));
            instance.width = ((Long) in.readValue((Long.class.getClassLoader())));
            return instance;
        }

        public MaxDimensions[] newArray(int size) {
            return (new MaxDimensions[size]);
        }

    }
    ;

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(height).append(width).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MaxDimensions) == false) {
            return false;
        }
        MaxDimensions rhs = ((MaxDimensions) other);
        return new EqualsBuilder().append(height, rhs.height).append(width, rhs.width).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(height);
        dest.writeValue(width);
    }

    public int describeContents() {
        return  0;
    }

}
