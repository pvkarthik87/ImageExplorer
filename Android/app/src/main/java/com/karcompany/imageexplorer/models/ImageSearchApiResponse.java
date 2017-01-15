/**
 * Created by pvkarthik on 2017-01-12.
 *
 * This is POJO class corresponding to server response (JSON).
 */
package com.karcompany.imageexplorer.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ImageSearchApiResponse implements Parcelable
{

    @SerializedName("result_count")
    @Expose
    private Long resultCount;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    public final static Creator<ImageSearchApiResponse> CREATOR = new Creator<ImageSearchApiResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ImageSearchApiResponse createFromParcel(Parcel in) {
            ImageSearchApiResponse instance = new ImageSearchApiResponse();
            instance.resultCount = ((Long) in.readValue((Long.class.getClassLoader())));
            in.readList(instance.images, (Image.class.getClassLoader()));
            return instance;
        }

        public ImageSearchApiResponse[] newArray(int size) {
            return (new ImageSearchApiResponse[size]);
        }

    }
    ;

    public Long getResultCount() {
        return resultCount;
    }

    public void setResultCount(Long resultCount) {
        this.resultCount = resultCount;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(resultCount).append(images).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ImageSearchApiResponse) == false) {
            return false;
        }
        ImageSearchApiResponse rhs = ((ImageSearchApiResponse) other);
        return new EqualsBuilder().append(resultCount, rhs.resultCount).append(images, rhs.images).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(resultCount);
        dest.writeList(images);
    }

    public int describeContents() {
        return  0;
    }

}
