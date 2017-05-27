package model;


import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Audio {

    private final String urlString;
    private URL url;
    private final AudioClip audioClip;

    public Audio(String urlString) {

        this.urlString = urlString;
        url = this.getClass().getResource(urlString);
        audioClip = Applet.newAudioClip(url);
    }

    public void play() {
        audioClip.play();
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getUrlString() {
        return urlString;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }
}
