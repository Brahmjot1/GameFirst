package com.player.cricketfirst.model;

import java.util.ArrayList;

public class CategoryModel {
    private  String isShowAds,id,title,url,icon,minPoint,isMultipleUsed,bgImage,icone,details,isUsed,description,shareMessage,shareImage,sharePoint,image,lable,matchId,offerId,screenNo,team1Name,team2Name,team1Image,team2Image,seriesName,viewCount,isShowDetails,startDate,isActive,note,noteColor;
    private String teamType,appName,amount,contestSize,contestCode,winAmount,isMultipal,isConfirmWinning,isShowEverytime,withdraw_type,btnName,packagename,isForce,withdrawId,txnID,couponeCode,deliveryDate,pointValue,isDeliverd,type,data,block_id,block_points,block_bg,earning_type,entryDate,points;
    private String isOnlyImg,imgUrl,clickUrl,homeNote,topImage,topUrl,appIndex,isAds,isBlink,stopPos;
    private String logo,btnColor,textColor,bgColor,backImage,frontImage,timer,adFailUrl,isShowIndstrial;
    String team1FullName,team2FullName,team1Score,team2Score,odd1,odd2,newsUrl;
    CategoryModel BottemBanner,topAds;
    private ArrayList<CategoryModel> block;

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getTeam1FullName() {
        return team1FullName;
    }

    public void setTeam1FullName(String team1FullName) {
        this.team1FullName = team1FullName;
    }

    public String getTeam2FullName() {
        return team2FullName;
    }

    public void setTeam2FullName(String team2FullName) {
        this.team2FullName = team2FullName;
    }

    public String getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    public String getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }

    public String getOdd1() {
        return odd1;
    }

    public void setOdd1(String odd1) {
        this.odd1 = odd1;
    }

    public String getOdd2() {
        return odd2;
    }

    public void setOdd2(String odd2) {
        this.odd2 = odd2;
    }

    public String getStopPos() {
        return stopPos;
    }

    public void setStopPos(String stopPos) {
        this.stopPos = stopPos;
    }

    public ArrayList<CategoryModel> getBlock() {
        return block;
    }

    public void setBlock(ArrayList<CategoryModel> block) {
        this.block = block;
    }

    public CategoryModel getBottemBanner() {
        return BottemBanner;
    }

    public String getAdFailUrl() {
        return adFailUrl;
    }

    public void setAdFailUrl(String adFailUrl) {
        this.adFailUrl = adFailUrl;
    }

    public String getIsShowIndstrial() {
        return isShowIndstrial;
    }

    public void setIsShowIndstrial(String isShowIndstrial) {
        this.isShowIndstrial = isShowIndstrial;
    }

    public void setBottemBanner(CategoryModel bottemBanner) {
        BottemBanner = bottemBanner;
    }

    public CategoryModel getTopAds() {
        return topAds;
    }

    public void setTopAds(CategoryModel topAds) {
        this.topAds = topAds;
    }

    public String getIsBlink() {
        return isBlink;
    }

    public void setIsBlink(String isBlink) {
        this.isBlink = isBlink;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBtnColor() {
        return btnColor;
    }

    public void setBtnColor(String btnColor) {
        this.btnColor = btnColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getIsAds() {
        return isAds;
    }
    public void setIsAds(String isAds) {
        this.isAds = isAds;
    }
    public String getIsblink() {
        return isBlink;
    }

    public void setIsblink(String isBlink) {
        this.isBlink = isBlink;
    }
    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getAppIndex() {
        return appIndex;
    }

    public void setAppIndex(String appIndex) {
        this.appIndex = appIndex;
    }

    public String getTopImage() {
        return topImage;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public String getTopUrl() {
        return topUrl;
    }

    public void setTopUrl(String topUrl) {
        this.topUrl = topUrl;
    }

    public String getHomeNote() {
        return homeNote;
    }

    public void setHomeNote(String homeNote) {
        this.homeNote = homeNote;
    }

    public String getIsOnlyImg() {
        return isOnlyImg;
    }

    public void setIsOnlyImg(String isOnlyImg) {
        this.isOnlyImg = isOnlyImg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContestSize() {
        return contestSize;
    }

    public void setContestSize(String contestSize) {
        this.contestSize = contestSize;
    }

    public String getContestCode() {
        return contestCode;
    }

    public void setContestCode(String contestCode) {
        this.contestCode = contestCode;
    }

    public String getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(String winAmount) {
        this.winAmount = winAmount;
    }

    public String getIsMultipal() {
        return isMultipal;
    }

    public void setIsMultipal(String isMultipal) {
        this.isMultipal = isMultipal;
    }

    public String getIsConfirmWinning() {
        return isConfirmWinning;
    }

    public void setIsConfirmWinning(String isConfirmWinning) {
        this.isConfirmWinning = isConfirmWinning;
    }

    public String getIsShowEverytime() {
        return isShowEverytime;
    }

    public void setIsShowEverytime(String isShowEverytime) {
        this.isShowEverytime = isShowEverytime;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getWithdraw_type() {
        return withdraw_type;
    }

    public void setWithdraw_type(String withdraw_type) {
        this.withdraw_type = withdraw_type;
    }

    public String getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(String withdrawId) {
        this.withdrawId = withdrawId;
    }

    public String getTxnID() {
        return txnID;
    }

    public void setTxnID(String txnID) {
        this.txnID = txnID;
    }

    public String getCouponeCode() {
        return couponeCode;
    }

    public void setCouponeCode(String couponeCode) {
        this.couponeCode = couponeCode;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPointValue() {
        return pointValue;
    }

    public void setPointValue(String pointValue) {
        this.pointValue = pointValue;
    }

    public String getIsDeliverd() {
        return isDeliverd;
    }

    public void setIsDeliverd(String isDeliverd) {
        this.isDeliverd = isDeliverd;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(String minPoint) {
        this.minPoint = minPoint;
    }

    public String getIsMultipleUsed() {
        return isMultipleUsed;
    }

    public void setIsMultipleUsed(String isMultipleUsed) {
        this.isMultipleUsed = isMultipleUsed;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEarning_type() {
        return earning_type;
    }

    public void setEarning_type(String earning_type) {
        this.earning_type = earning_type;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShareMessage() {
        return shareMessage;
    }

    public void setShareMessage(String shareMessage) {
        this.shareMessage = shareMessage;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public String getSharePoint() {
        return sharePoint;
    }

    public void setSharePoint(String sharePoint) {
        this.sharePoint = sharePoint;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public String getBlock_points() {
        return block_points;
    }

    public void setBlock_points(String block_points) {
        this.block_points = block_points;
    }

    public String getBlock_bg() {
        return block_bg;
    }

    public void setBlock_bg(String block_bg) {
        this.block_bg = block_bg;
    }

    public String getTeamType() {
        return teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getScreenNo() {
        return screenNo;
    }

    public void setScreenNo(String screenNo) {
        this.screenNo = screenNo;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getTeam1Image() {
        return team1Image;
    }

    public void setTeam1Image(String team1Image) {
        this.team1Image = team1Image;
    }

    public String getTeam2Image() {
        return team2Image;
    }

    public void setTeam2Image(String team2Image) {
        this.team2Image = team2Image;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getIsShowDetails() {
        return isShowDetails;
    }

    public void setIsShowDetails(String isShowDetails) {
        this.isShowDetails = isShowDetails;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(String noteColor) {
        this.noteColor = noteColor;
    }

    public String getIsShowAds() {
        return isShowAds;
    }

    public void setIsShowAds(String isShowAds) {
        this.isShowAds = isShowAds;
    }
}
