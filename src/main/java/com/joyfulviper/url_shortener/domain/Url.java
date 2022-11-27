package com.joyfulviper.url_shortener.domain;

//import lombok.Builder;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;

//@Builder
//@Getter
//@EqualsAndHashCode
public class Url {
    private String originalUrl;
    private String shortenUrl;
    private int requestCount;

    public String getOriginalUrl() {
        return this.originalUrl;
    }

    public String getShortenUrl() {
        return this.shortenUrl;
    }

    public int getRequestCount() {
        return this.requestCount;
    }

    private Url(Builder builder) {
        this.originalUrl = builder.originalUrl;
        this.shortenUrl = builder.shortenUrl;
        this.requestCount = builder.requestCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String originalUrl;
        private String shortenUrl;
        private int requestCount;

        private Builder() {};

        public Builder originalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
            return this;
        }

        public Builder shortenUrl(String shortenUrl) {
            this.shortenUrl = shortenUrl;
            return this;
        }

        public Builder requestCount(int requestCount) {
            this.requestCount = requestCount;
            return this;
        }

        public Url build() {
            return new Url(this);
        }
    }
}
