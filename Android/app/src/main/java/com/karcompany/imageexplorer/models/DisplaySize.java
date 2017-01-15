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

public class DisplaySize implements Parcelable
{

    @SerializedName("is_watermarked")
    @Expose
    private Boolean isWatermarked;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uri")
    @Expose
    private String uri;
    public final static Creator<DisplaySize> CREATOR = new Creator<DisplaySize>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DisplaySize createFromParcel(Parcel in) {
            DisplaySize instance = new DisplaySize();
            instance.isWatermarked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.uri = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public DisplaySize[] newArray(int size) {
            return (new DisplaySize[size]);
        }

    }
    ;

    public Boolean getIsWatermarked() {
        return isWatermarked;
    }

    public void setIsWatermarked(Boolean isWatermarked) {
        this.isWatermarked = isWatermarked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isWatermarked).append(name).append(uri).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DisplaySize) == false) {
            return false;
        }
        DisplaySize rhs = ((DisplaySize) other);
        return new EqualsBuilder().append(isWatermarked, rhs.isWatermarked).append(name, rhs.name).append(uri, rhs.uri).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isWatermarked);
        dest.writeValue(name);
        dest.writeValue(uri);
    }

    public int describeContents() {
        return  0;
    }

}
