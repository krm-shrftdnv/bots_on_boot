package kpfu.itis.g804.bots_project.service;

import net.dv8tion.jda.api.entities.Member;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

public class Helper {

    private static final String urlString = "https://engine.lifeis.porn/api/four_pics.php";
    public static final int maxId = 22;
    private static int lastId = -1;

    //https://engine.lifeis.porn/api/database/four_pics/img/game/герой.jpg
    public static String sendRequestForImages() {
        lastId = -1;
        return sendRequestForImages(lastId);
    }

    public static String sendRequestForImages(int lastId) {
        String img = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // создаем объект клиента
            HttpGet request;
            if (lastId >= 0) {
                request = new HttpGet(urlString + "?lastid=" + lastId);
            } else {
                request = new HttpGet(urlString);
            }
            // добавляем заголовки запроса
            request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                // если есть тело ответа
                if (entity != null) {
                    // возвращаем строку
                    String result = EntityUtils.toString(entity);
                    JSONParser parser = new JSONParser();
                    System.out.println(result);
                    JSONObject responseJson = (JSONObject) parser.parse(result);
                    if ((boolean) responseJson.get("ok")) {
                        JSONObject data = (JSONObject) responseJson.get("data");
                        long id = (long) data.get("id");
                        img = (String) data.get("img");
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static String parseImgUrl(String imgUrl) {
        return imgUrl.substring(imgUrl.indexOf("/game")+6, imgUrl.indexOf(".jpg"));
    }

    public static Optional<Member> getMemberById(Long memberId, List<Member>members){
        for(Member member : members) {
            if (member.getIdLong() == memberId) return Optional.of(member);
        }
        return Optional.empty();
    }

}
