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

public class Image implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("asset_family")
    @Expose
    private String assetFamily;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("collection_code")
    @Expose
    private String collectionCode;
    @SerializedName("collection_id")
    @Expose
    private Long collectionId;
    @SerializedName("collection_name")
    @Expose
    private String collectionName;
    @SerializedName("display_sizes")
    @Expose
    private List<DisplaySize> displaySizes = null;
    @SerializedName("license_model")
    @Expose
    private String licenseModel;
    @SerializedName("max_dimensions")
    @Expose
    private MaxDimensions maxDimensions;
    @SerializedName("title")
    @Expose
    private String title;
    public final static Creator<Image> CREATOR = new Creator<Image>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Image createFromParcel(Parcel in) {
            Image instance = new Image();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.assetFamily = ((String) in.readValue((String.class.getClassLoader())));
            instance.caption = ((String) in.readValue((String.class.getClassLoader())));
            instance.collectionCode = ((String) in.readValue((String.class.getClassLoader())));
            instance.collectionId = ((Long) in.readValue((Long.class.getClassLoader())));
            instance.collectionName = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.displaySizes, (DisplaySize.class.getClassLoader()));
            instance.licenseModel = ((String) in.readValue((String.class.getClassLoader())));
            instance.maxDimensions = ((MaxDimensions) in.readValue((MaxDimensions.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    }
    ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetFamily() {
        return assetFamily;
    }

    public void setAssetFamily(String assetFamily) {
        this.assetFamily = assetFamily;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCollectionCode() {
        return collectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        this.collectionCode = collectionCode;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<DisplaySize> getDisplaySizes() {
        return displaySizes;
    }

    public void setDisplaySizes(List<DisplaySize> displaySizes) {
        this.displaySizes = displaySizes;
    }

    public String getLicenseModel() {
        return licenseModel;
    }

    public void setLicenseModel(String licenseModel) {
        this.licenseModel = licenseModel;
    }

    public MaxDimensions getMaxDimensions() {
        return maxDimensions;
    }

    public void setMaxDimensions(MaxDimensions maxDimensions) {
        this.maxDimensions = maxDimensions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(assetFamily).append(caption).append(collectionCode).append(collectionId).append(collectionName).append(displaySizes).append(licenseModel).append(maxDimensions).append(title).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Image) == false) {
            return false;
        }
        Image rhs = ((Image) other);
        return new EqualsBuilder().append(id, rhs.id).append(assetFamily, rhs.assetFamily).append(caption, rhs.caption).append(collectionCode, rhs.collectionCode).append(collectionId, rhs.collectionId).append(collectionName, rhs.collectionName).append(displaySizes, rhs.displaySizes).append(licenseModel, rhs.licenseModel).append(maxDimensions, rhs.maxDimensions).append(title, rhs.title).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(assetFamily);
        dest.writeValue(caption);
        dest.writeValue(collectionCode);
        dest.writeValue(collectionId);
        dest.writeValue(collectionName);
        dest.writeList(displaySizes);
        dest.writeValue(licenseModel);
        dest.writeValue(maxDimensions);
        dest.writeValue(title);
    }

    public int describeContents() {
        return  0;
    }

}
