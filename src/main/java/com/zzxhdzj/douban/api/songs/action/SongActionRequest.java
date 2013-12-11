package com.zzxhdzj.douban.api.songs.action;

import android.content.Context;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongActionRequest extends AuthApiRequest<TextApiResponse> {
    private SongActionType songActionType;
    private final int currentChannelId;
    private final int songId;

    public SongActionRequest(SongActionType songActionType, int currentChannelId, int songId,Context context) {
        super(context);
        this.songActionType = songActionType;
        this.currentChannelId = currentChannelId;
        this.songId = songId;
    }

    @Override
    public String getUrlString() {
        return Constants.SONG_ACTION_URL
                +"&type="+songActionType.getCode()
                +"&channel="+currentChannelId
                +"&sid="+songId;
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return null;
    }
}

