package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "postal_code",
        "country",
        "country_iso_code",
        "federal_district",
        "region_fias_id",
        "region_kladr_id",
        "region_iso_code",
        "region_with_type",
        "region_type",
        "region_type_full",
        "region",
        "area_fias_id",
        "area_kladr_id",
        "area_with_type",
        "area_type",
        "area_type_full",
        "area",
        "city_fias_id",
        "city_kladr_id",
        "city_with_type",
        "city_type",
        "city_type_full",
        "city",
        "city_area",
        "city_district_fias_id",
        "city_district_kladr_id",
        "city_district_with_type",
        "city_district_type",
        "city_district_type_full",
        "city_district",
        "settlement_fias_id",
        "settlement_kladr_id",
        "settlement_with_type",
        "settlement_type",
        "settlement_type_full",
        "settlement",
        "street_fias_id",
        "street_kladr_id",
        "street_with_type",
        "street_type",
        "street_type_full",
        "street",
        "stead_fias_id",
        "stead_cadnum",
        "stead_type",
        "stead_type_full",
        "stead",
        "house_fias_id",
        "house_kladr_id",
        "house_cadnum",
        "house_type",
        "house_type_full",
        "house",
        "block_type",
        "block_type_full",
        "block",
        "entrance",
        "floor",
        "flat_fias_id",
        "flat_cadnum",
        "flat_type",
        "flat_type_full",
        "flat",
        "flat_area",
        "square_meter_price",
        "flat_price",
        "postal_box",
        "fias_id",
        "fias_code",
        "fias_level",
        "fias_actuality_state",
        "kladr_id",
        "geoname_id",
        "capital_marker",
        "okato",
        "oktmo",
        "tax_office",
        "tax_office_legal",
        "timezone",
        "geo_lat",
        "geo_lon",
        "beltway_hit",
        "beltway_distance",
        "metro",
        "divisions",
        "qc_geo",
        "qc_complete",
        "qc_house",
        "history_values",
        "unparsed_parts",
        "source",
        "qc"
})
@Generated("jsonschema2pojo")
public class Data {

    @JsonProperty("postal_code")
    public String postalCode;
    @JsonProperty("country")
    public String country;
    @JsonProperty("country_iso_code")
    public String countryIsoCode;
    @JsonProperty("federal_district")
    public String federalDistrict;
    @JsonProperty("region_fias_id")
    public String regionFiasId;
    @JsonProperty("region_kladr_id")
    public String regionKladrId;
    @JsonProperty("region_iso_code")
    public String regionIsoCode;
    @JsonProperty("region_with_type")
    public String regionWithType;
    @JsonProperty("region_type")
    public String regionType;
    @JsonProperty("region_type_full")
    public String regionTypeFull;
    @JsonProperty("region")
    public String region;
    @JsonProperty("area_fias_id")
    public Object areaFiasId;
    @JsonProperty("area_kladr_id")
    public Object areaKladrId;
    @JsonProperty("area_with_type")
    public Object areaWithType;
    @JsonProperty("area_type")
    public Object areaType;
    @JsonProperty("area_type_full")
    public Object areaTypeFull;
    @JsonProperty("area")
    public Object area;
    @JsonProperty("city_fias_id")
    public String cityFiasId;
    @JsonProperty("city_kladr_id")
    public String cityKladrId;
    @JsonProperty("city_with_type")
    public String cityWithType;
    @JsonProperty("city_type")
    public String cityType;
    @JsonProperty("city_type_full")
    public String cityTypeFull;
    @JsonProperty("city")
    public String city;
    @JsonProperty("city_area")
    public Object cityArea;
    @JsonProperty("city_district_fias_id")
    public Object cityDistrictFiasId;
    @JsonProperty("city_district_kladr_id")
    public Object cityDistrictKladrId;
    @JsonProperty("city_district_with_type")
    public String cityDistrictWithType;
    @JsonProperty("city_district_type")
    public String cityDistrictType;
    @JsonProperty("city_district_type_full")
    public String cityDistrictTypeFull;
    @JsonProperty("city_district")
    public String cityDistrict;
    @JsonProperty("settlement_fias_id")
    public Object settlementFiasId;
    @JsonProperty("settlement_kladr_id")
    public Object settlementKladrId;
    @JsonProperty("settlement_with_type")
    public Object settlementWithType;
    @JsonProperty("settlement_type")
    public Object settlementType;
    @JsonProperty("settlement_type_full")
    public Object settlementTypeFull;
    @JsonProperty("settlement")
    public Object settlement;
    @JsonProperty("street_fias_id")
    public String streetFiasId;
    @JsonProperty("street_kladr_id")
    public String streetKladrId;
    @JsonProperty("street_with_type")
    public String streetWithType;
    @JsonProperty("street_type")
    public String streetType;
    @JsonProperty("street_type_full")
    public String streetTypeFull;
    @JsonProperty("street")
    public String street;
    @JsonProperty("stead_fias_id")
    public Object steadFiasId;
    @JsonProperty("stead_cadnum")
    public Object steadCadnum;
    @JsonProperty("stead_type")
    public Object steadType;
    @JsonProperty("stead_type_full")
    public Object steadTypeFull;
    @JsonProperty("stead")
    public Object stead;
    @JsonProperty("house_fias_id")
    public String houseFiasId;
    @JsonProperty("house_kladr_id")
    public String houseKladrId;
    @JsonProperty("house_cadnum")
    public Object houseCadnum;
    @JsonProperty("house_type")
    public String houseType;
    @JsonProperty("house_type_full")
    public String houseTypeFull;
    @JsonProperty("house")
    public String house;
    @JsonProperty("block_type")
    public Object blockType;
    @JsonProperty("block_type_full")
    public Object blockTypeFull;
    @JsonProperty("block")
    public Object block;
    @JsonProperty("entrance")
    public Object entrance;
    @JsonProperty("floor")
    public Object floor;
    @JsonProperty("flat_fias_id")
    public Object flatFiasId;
    @JsonProperty("flat_cadnum")
    public Object flatCadnum;
    @JsonProperty("flat_type")
    public Object flatType;
    @JsonProperty("flat_type_full")
    public Object flatTypeFull;
    @JsonProperty("flat")
    public Object flat;
    @JsonProperty("flat_area")
    public Object flatArea;
    @JsonProperty("square_meter_price")
    public Object squareMeterPrice;
    @JsonProperty("flat_price")
    public Object flatPrice;
    @JsonProperty("postal_box")
    public Object postalBox;
    @JsonProperty("fias_id")
    public String fiasId;
    @JsonProperty("fias_code")
    public Object fiasCode;
    @JsonProperty("fias_level")
    public String fiasLevel;
    @JsonProperty("fias_actuality_state")
    public String fiasActualityState;
    @JsonProperty("kladr_id")
    public String kladrId;
    @JsonProperty("geoname_id")
    public String geonameId;
    @JsonProperty("capital_marker")
    public String capitalMarker;
    @JsonProperty("okato")
    public String okato;
    @JsonProperty("oktmo")
    public String oktmo;
    @JsonProperty("tax_office")
    public String taxOffice;
    @JsonProperty("tax_office_legal")
    public String taxOfficeLegal;
    @JsonProperty("timezone")
    public Object timezone;
    @JsonProperty("geo_lat")
    public String geoLat;
    @JsonProperty("geo_lon")
    public String geoLon;
    @JsonProperty("beltway_hit")
    public Object beltwayHit;
    @JsonProperty("beltway_distance")
    public Object beltwayDistance;
    @JsonProperty("metro")
    public Object metro;
    @JsonProperty("divisions")
    public Object divisions;
    @JsonProperty("qc_geo")
    public String qcGeo;
    @JsonProperty("qc_complete")
    public Object qcComplete;
    @JsonProperty("qc_house")
    public Object qcHouse;
    @JsonProperty("history_values")
    public Object historyValues;
    @JsonProperty("unparsed_parts")
    public Object unparsedParts;
    @JsonProperty("source")
    public Object source;
    @JsonProperty("qc")
    public Object qc;

}
