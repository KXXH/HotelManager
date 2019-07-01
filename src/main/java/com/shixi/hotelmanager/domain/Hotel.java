package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_hotelinfo")
public class Hotel extends Model<Hotel> {
    @TableId("id")
    private int id;

    private int hotelId;

    private String chainName;

    private int brandId;

    private String brandName;

    private String hotelName;

    private String hotelFormerlyName;

    private String hotelTranslatedName;

    private String addressline1;

    private String addressline2;

    private String zipcode;

    private String city;

    private String state;

    private String country;

    private String countryisocode;

    private double starRating;

    private double longitude;

    private double latitude;

    @Pattern(regexp = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?",message="URL格式不正确")
    private String url;

    private Time checkin;

    private Time checkout;

    private int numberrooms;

    private int numberfloors;

    private int yearopened;

    private int yearrenovated;

    private String photo1;

    private String photo2;

    private String photo3;

    private String photo4;

    private String photo5;

    private String overview;

    private int ratesFrom;

    private int continentId;

    private String continent_name;

    private int cityId;

    private int countryId;

    private int numberOfReviews;

    private double ratingAverage;

    private String ratesCurrency;

    @TableField(exist = false)
    private int remain;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
