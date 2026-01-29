package tictactoe;

import javafx.scene.media.AudioClip;

public class SoundPlayer {

    private static final AudioClip click =
            new AudioClip(
                    SoundPlayer.class
                            .getResource("/assets/click.wav")
                            .toExternalForm()
            );

    private static final AudioClip win =
            new AudioClip(
                    SoundPlayer.class
                            .getResource("/assets/win.wav")
                            .toExternalForm()
            );

    public static void playClick() {
        click.play();
    }

    public static void playWin() {
        win.play();
    }
}
