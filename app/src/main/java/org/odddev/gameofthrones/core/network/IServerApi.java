package org.odddev.gameofthrones.core.network;

import org.odddev.gameofthrones.splash.data.Character;
import org.odddev.gameofthrones.splash.data.House;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

public interface IServerApi {

    @GET("houses/{houseId}")
    Observable<House> getHouse(@Path("houseId") /*String*/int houseId);

    @GET("characters/{characterId}")
    Observable<Character> getCharacter(@Path("characterId") int characterId);
}
