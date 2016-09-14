package com.tj.uimading.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class Data {
    public String digest;
    public String imgsrc;
    public String lmodify;
    public String replyCount;
    public String votecount;
    public String title;
    public String tname;
    public String url;
    public String postid;
    public String docid;
    public String boardid;
    public List<Image> imgextra;
    public List<ADS> ads;

    public Data(String digest, String imgsrc, String lmodify, String replyCount, String votecount, String title, String tname, String url, String postid,String docid,String boardid,List<Image> imgextra, List<ADS> ads) {
        this.digest = digest;
        this.imgsrc = imgsrc;
        this.lmodify = lmodify;
        this.replyCount = replyCount;
        this.votecount = votecount;
        this.title = title;
        this.tname = tname;
        this.url = url;
        this.postid = postid;
        this.imgextra = imgextra;
        this.ads = ads;
        this.docid=docid;
        this.boardid=boardid;
    }

    public static class ADS {
        public ADS(String title, String tag, String imgsrc, String subtitle) {
            this.title = title;
            this.tag = tag;
            this.imgsrc = imgsrc;
            this.subtitle = subtitle;
        }

        public String title;
        public String tag;
        public String imgsrc;
        public String subtitle;
    }

    public static class Image {
        public Image(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String imgsrc;
    }
}
