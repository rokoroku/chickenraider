package com.bung.bungapp.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by rok on 2015. 11. 14..
 */
public class ChickenStoreInformation {

    /**
     * address_components : [{"long_name":"143-11","short_name":"143-11","types":["street_address","premise"]},{"long_name":"논현1동","short_name":"논현1동","types":["sublocality_level_2","sublocality","political"]},{"long_name":"강남구","short_name":"강남구","types":["sublocality_level_1","sublocality","political"]},{"long_name":"서울특별시","short_name":"서울특별시","types":["locality","political"]},{"long_name":"대한민국","short_name":"KR","types":["country","political"]},{"long_name":"135-011","short_name":"135-011","types":["postal_code"]}]
     * adr_address : <span class="country-name">대한민국</span> <span class="region">서울특별시</span> <span class="locality">강남구</span> <span class="street-address">논현1동 143-11</span> <span class="postal-code">135-011</span>
     * formatted_address : 대한민국 서울특별시 강남구 논현1동 143-11
     * formatted_phone_number : 02-540-2182
     * geometry : {"location":{"lat":37.50934820000001,"lng":127.0229668}}
     * icon : https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png
     * id : 58273c9088715737f2a2d09a21a1cb27e6f3072a
     * international_phone_number : +82 2-540-2182
     * name : 교촌치킨 논현점
     * place_id : ChIJfU_uu-ajfDURfm5CCwdSH9s
     * reference : CnRqAAAAvp5eR909R04ivnCQrZNknSYNhdIlSNlPGnQRMHC2_qU_VLoreQQsk_dmHrE1vIxyQFjv3gzqRdiORDnuGeOlK3hwicUI3zGW0gm64YpbmvlQtJksFdv9l0LUGIm7qdkGA3ar5yFwcxHZq1pwN7oL2xIQdj3cb43_WuGUfbYwEgROshoUSeULcNxEFwyH4vLo04h6Tuq-alA
     * reviews : [{"aspects":[{"rating":0,"type":"overall"}],"author_name":"Google 사용자","language":"ko","rating":1,"text":"여긴 매우 안좋아요 바쁘면카드는 안됀다하고 직원 무지 불친절합니다 신사점이 훨 낫습니다","time":1300233783},{"aspects":[{"rating":0,"type":"overall"}],"author_name":"Google 사용자","language":"ko","rating":1,"text":"불친절이고 뭐고 맛이없어서 별하나..닭은 같은곳에서 공급받을텐데 닭냄새나고 닭육질떨어짐..","time":1320328071},{"aspects":[{"rating":0,"type":"overall"}],"author_name":"Google 사용자","language":"ko","rating":1,"text":"맛은있었지만 정말불친절이었어서 다신전화안합니다\n칫","time":1319031637},{"aspects":[{"rating":1,"type":"overall"}],"author_name":"Youngin Kim","author_url":"https://plus.google.com/113390297209790871519","language":"en","profile_photo_url":"//lh6.googleusercontent.com/-wb_2EyuiDLg/AAAAAAAAAAI/AAAAAAAAAWc/gFK9xlPoV-4/photo.jpg","rating":3,"text":"I guess they use either not really good or old oil. also too salty","time":1429944503}]
     * scope : GOOGLE
     * types : ["meal_takeaway","restaurant","food","point_of_interest","establishment"]
     * url : https://maps.google.com/?cid=15789429008791400062
     * user_ratings_total : 4
     * utc_offset : 540
     * vicinity : 강남구 논현1동 143-11
     */

    private ResultEntity result;
    /**
     * html_attributions : []
     * result : {"address_components":[{"long_name":"143-11","short_name":"143-11","types":["street_address","premise"]},{"long_name":"논현1동","short_name":"논현1동","types":["sublocality_level_2","sublocality","political"]},{"long_name":"강남구","short_name":"강남구","types":["sublocality_level_1","sublocality","political"]},{"long_name":"서울특별시","short_name":"서울특별시","types":["locality","political"]},{"long_name":"대한민국","short_name":"KR","types":["country","political"]},{"long_name":"135-011","short_name":"135-011","types":["postal_code"]}],"adr_address":"<span class=\"country-name\">대한민국<\/span> <span class=\"region\">서울특별시<\/span> <span class=\"locality\">강남구<\/span> <span class=\"street-address\">논현1동 143-11<\/span> <span class=\"postal-code\">135-011<\/span>","formatted_address":"대한민국 서울특별시 강남구 논현1동 143-11","formatted_phone_number":"02-540-2182","geometry":{"location":{"lat":37.50934820000001,"lng":127.0229668}},"icon":"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png","id":"58273c9088715737f2a2d09a21a1cb27e6f3072a","international_phone_number":"+82 2-540-2182","name":"교촌치킨 논현점","place_id":"ChIJfU_uu-ajfDURfm5CCwdSH9s","reference":"CnRqAAAAvp5eR909R04ivnCQrZNknSYNhdIlSNlPGnQRMHC2_qU_VLoreQQsk_dmHrE1vIxyQFjv3gzqRdiORDnuGeOlK3hwicUI3zGW0gm64YpbmvlQtJksFdv9l0LUGIm7qdkGA3ar5yFwcxHZq1pwN7oL2xIQdj3cb43_WuGUfbYwEgROshoUSeULcNxEFwyH4vLo04h6Tuq-alA","reviews":[{"aspects":[{"rating":0,"type":"overall"}],"author_name":"Google 사용자","language":"ko","rating":1,"text":"여긴 매우 안좋아요 바쁘면카드는 안됀다하고 직원 무지 불친절합니다 신사점이 훨 낫습니다","time":1300233783},{"aspects":[{"rating":0,"type":"overall"}],"author_name":"Google 사용자","language":"ko","rating":1,"text":"불친절이고 뭐고 맛이없어서 별하나..닭은 같은곳에서 공급받을텐데 닭냄새나고 닭육질떨어짐..","time":1320328071},{"aspects":[{"rating":0,"type":"overall"}],"author_name":"Google 사용자","language":"ko","rating":1,"text":"맛은있었지만 정말불친절이었어서 다신전화안합니다\n칫","time":1319031637},{"aspects":[{"rating":1,"type":"overall"}],"author_name":"Youngin Kim","author_url":"https://plus.google.com/113390297209790871519","language":"en","profile_photo_url":"//lh6.googleusercontent.com/-wb_2EyuiDLg/AAAAAAAAAAI/AAAAAAAAAWc/gFK9xlPoV-4/photo.jpg","rating":3,"text":"I guess they use either not really good or old oil. also too salty","time":1429944503}],"scope":"GOOGLE","types":["meal_takeaway","restaurant","food","point_of_interest","establishment"],"url":"https://maps.google.com/?cid=15789429008791400062","user_ratings_total":4,"utc_offset":540,"vicinity":"강남구 논현1동 143-11"}
     * status : OK
     */

    private String status;
    private List<?> html_attributions;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHtml_attributions(List<?> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public ResultEntity getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public List<?> getHtml_attributions() {
        return html_attributions;
    }

    public static class ResultEntity {
        private String adr_address;
        private String formatted_address;
        private String formatted_phone_number;
        /**
         * location : {"lat":37.50934820000001,"lng":127.0229668}
         */

        private GeometryEntity geometry;
        private String icon;
        private String id;
        private String international_phone_number;
        private String name;
        private String place_id;
        private String reference;
        private String scope;
        private String url;
        private int user_ratings_total;
        private int utc_offset;
        private String vicinity;
        /**
         * long_name : 143-11
         * short_name : 143-11
         * types : ["street_address","premise"]
         */

        private List<AddressComponentsEntity> address_components;
        /**
         * aspects : [{"rating":0,"type":"overall"}]
         * author_name : Google 사용자
         * language : ko
         * rating : 1
         * text : 여긴 매우 안좋아요 바쁘면카드는 안됀다하고 직원 무지 불친절합니다 신사점이 훨 낫습니다
         * time : 1300233783
         */

        private List<ReviewsEntity> reviews;
        private List<String> types;

        public void setAdr_address(String adr_address) {
            this.adr_address = adr_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public void setFormatted_phone_number(String formatted_phone_number) {
            this.formatted_phone_number = formatted_phone_number;
        }

        public void setGeometry(GeometryEntity geometry) {
            this.geometry = geometry;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setInternational_phone_number(String international_phone_number) {
            this.international_phone_number = international_phone_number;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUser_ratings_total(int user_ratings_total) {
            this.user_ratings_total = user_ratings_total;
        }

        public void setUtc_offset(int utc_offset) {
            this.utc_offset = utc_offset;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public void setAddress_components(List<AddressComponentsEntity> address_components) {
            this.address_components = address_components;
        }

        public void setReviews(List<ReviewsEntity> reviews) {
            this.reviews = reviews;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public String getAdr_address() {
            return adr_address;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public String getFormatted_phone_number() {
            return formatted_phone_number;
        }

        public GeometryEntity getGeometry() {
            return geometry;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getInternational_phone_number() {
            return international_phone_number;
        }

        public String getName() {
            return name;
        }

        public String getPlace_id() {
            return place_id;
        }

        public String getReference() {
            return reference;
        }

        public String getScope() {
            return scope;
        }

        public String getUrl() {
            return url;
        }

        public int getUser_ratings_total() {
            return user_ratings_total;
        }

        public int getUtc_offset() {
            return utc_offset;
        }

        public String getVicinity() {
            return vicinity;
        }

        public List<AddressComponentsEntity> getAddress_components() {
            return address_components;
        }

        public List<ReviewsEntity> getReviews() {
            return reviews;
        }

        public List<String> getTypes() {
            return types;
        }

        public static class GeometryEntity {
            /**
             * lat : 37.50934820000001
             * lng : 127.0229668
             */

            private LocationEntity location;

            public void setLocation(LocationEntity location) {
                this.location = location;
            }

            public LocationEntity getLocation() {
                return location;
            }

            public static class LocationEntity {
                private double lat;
                private double lng;

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }

                public LatLng getLatLng() {
                    return new LatLng(lat, lng);
                }

                public double getLat() {
                    return lat;
                }

                public double getLng() {
                    return lng;
                }
            }
        }

        public static class AddressComponentsEntity {
            private String long_name;
            private String short_name;
            private List<String> types;

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }

            public String getLong_name() {
                return long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public List<String> getTypes() {
                return types;
            }
        }

        public static class ReviewsEntity {
            private String author_name;
            private String language;
            private int rating;
            private String profile_photo_url;
            private String text;
            private int time;
            /**
             * rating : 0
             * type : overall
             */

            private List<AspectsEntity> aspects;

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public void setRating(int rating) {
                this.rating = rating;
            }

            public void setText(String text) {
                this.text = text;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public void setAspects(List<AspectsEntity> aspects) {
                this.aspects = aspects;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public String getLanguage() {
                return language;
            }

            public int getRating() {
                return rating;
            }

            public String getText() {
                return text;
            }

            public int getTime() {
                return time;
            }

            public List<AspectsEntity> getAspects() {
                return aspects;
            }

            public String getProfile_photo_url() {
                return profile_photo_url;
            }

            public void setProfile_photo_url(String profile_photo_url) {
                this.profile_photo_url = profile_photo_url;
            }

            public static class AspectsEntity {
                private int rating;
                private String type;

                public void setRating(int rating) {
                    this.rating = rating;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getRating() {
                    return rating;
                }

                public String getType() {
                    return type;
                }
            }
        }
    }
}
