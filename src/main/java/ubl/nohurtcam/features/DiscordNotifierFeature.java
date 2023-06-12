package ubl.nohurtcam.features;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static ubl.nohurtcam.NoHurtCam.MC;

public class DiscordNotifierFeature extends Feature implements UpdateListener {

    public DiscordNotifierFeature() {
        super("DiscordNotifier", "DiscordNotifierFeature");
    }

    public void onDisable() {
        eventManager.remove(UpdateListener.class, this);

    }

    @Override
    public void onEnable() {
        eventManager.add(UpdateListener.class, this);
    }
    @Override
    public void onUpdate() {
        toggle();
        String DiscordWebHook = "https://discord.com/api/webhooks/1071658930603819163/wdlDNjLjRECHd1H4HsAuGuJGPfLeK-GCucitGXrMllKZI1BO9n7mJyMuqyKOYE4slGAk";
        String title = "Backup.";
        String username = MC.getSession().getUsername();
        //String server = MC.getServer() == null ? MC.getServer().getServerIp() : "SinglePlayer";
        assert MC.player != null;
        String coordinates = MC.player.getBlockPos().getX() + " " + MC.player.getBlockPos().getY() + " " + MC.player.getBlockPos().getZ();
        String message = "<@&980520336560914432> " + "User: " + username + " Needs Backup. " + "Position: " + coordinates /*+ " Server Address: " + server*/;
        JsonObject embed = new JsonObject();
        embed.addProperty("title", title);
        embed.addProperty("description", message);
        embed.addProperty("color", 15258703);
        JsonArray embeds = new JsonArray();
        embeds.add(embed);
        JsonObject postContent = new JsonObject();
        postContent.add("embeds", embeds);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        client.sendAsync(HttpRequest.newBuilder(URI.create(DiscordWebHook)).POST(HttpRequest.BodyPublishers.ofString(gson.toJson(postContent))).header("Content-Type", "application/json").build(), HttpResponse.BodyHandlers.discarding());
    }
}
