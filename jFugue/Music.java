import org.jfugue.player.Player;
import org.jfugue.pattern.Pattern;
import org.jfugue.rhythm.Rhythm;
import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import org.jfugue.midi.MidiFileManager;

public class Music {

    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        Player player = new Player();
        Pattern jadoo = new Pattern("V0 I[Piano] B C F Eh R | B C E Dh R | B C Fqi R | E D Ew ");
        Pattern p1 = new Pattern("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq");
        Pattern p2 = new Pattern("V1 I[Flute] Rw     | Rw     | GmajQQQ  CmajQ");
        Pattern omd1 = new Pattern("T275 C R C R C R G4 R A4 R A4 R G4 R R");
        Pattern omd2 = new Pattern("T275 E R E R D R D R C R R");
        //player.play(jadoo);
        //player.play(p1,p2);
        //player.play(omd1,omd2,new Pattern("G4 R"),omd1,omd2);
        
        Pattern papers = MidiFileManager.loadPatternFromMidi(new File("Papers_Please.mid"));
        //System.out.println(papers);
        //player.play(papers);

        Rhythm rhythm = new Rhythm()
        .addLayer("O..oO...O..oOO..")
        .addLayer("..S...S...S...S.")
        .addLayer("````````````````")
        .addLayer("...............+");
        //player.play(rhythm.getPattern());

        Pattern b = new Pattern("V0 T480 I[Piano] R R R R R R R R R R");
        Pattern m1 = new Pattern("V0 T480 I[Piano] D4 D4 D R A4 R R G#4 R G4 R F4 R D4 F4 G4 C4 C4 D R A4 R R G#4 R G4");
        Pattern m2 = new Pattern("V0 T480 I[Violin] R F4 R D4 F4 G4 B3 B3 D R A4 R R G#4 R G4 R F4 R D4 F4 G4 A#3 A#3 D R");
        Pattern m3 = new Pattern("V0 T480 I[Overdriven_Guitar] A4 R R G#4 R G4 R F4 R D4 F4 G4 D4 D4 D R A4 R R G#4 R G4 R F4 R D4");
        Pattern m4 = new Pattern("V0 T480 I[Overdriven_Guitar] F4 G4 C4 C4 D R A4 R R G#4 R G4 R F4 R D4 F4 G4 B3 B3 D R A4 R R G#4");
        Pattern m5 = new Pattern("V0 T480 I[Guitar] R G4 R F4 R D4 F4 G4 A#3 A#3 D R A4 R R G#4 R G4 R F4 R D4 F4 G4 D4 D4");
        Pattern m6 = new Pattern("V0 T480 I[Piano] D R A4 R R G#4 R G4 R F4 R D4 F4 G4 C4 C4 D R A4 R R G#4 R G4 R F4");
        Pattern m7 = new Pattern("V0 T480 I[Piano] R D4 F4 G4 B3 B3 D R A4 R R G#4 R G4 R F4 R D4 F4 G4 A#3 A#3 D R A4 R");
        Pattern m8 = new Pattern("V0 T480 I[Violin] R G#4 R G4 R F4 R D4 F4 G4 D4 D4 D R A4 R R G#4 R G4 R F4 R D4 F4 G4");
        Pattern m9 = new Pattern("V0 T480 I[Violin] C4 C4 D R A4 R R G#4 R G4 R F4 R D4 F4 G4 B3 B3 D R A4 R R G#4 R G4");
        Pattern m10 = new Pattern("V0 T480 I[Violin] R F4 R D4 F4 G4 A#3 A#3 D R A4 R R G#4 R G4 R F4 R D4 F4 G4 F4 R F4 F4");
        Pattern m11 = new Pattern("V0 T480 I[Piano] R F4 R F4 R D4 R D4 R R D4 R F4 F4 F4 F4 R G4 R G#4 R G4 F4 D4 F4 G4");
        Pattern m12 = new Pattern("V0 T480 I[Piano] R R F4 R F4 F4 R G4 R G#4 R A4 R C R A4 R R D R D R D A4 D C ");
        Pattern m13 = new Pattern("V0 T480 I[Piano] R R R R R R R R A4 R A4 A4 R A4 R A4 R G4 R G4 R R R R A4 R");
        Pattern m14 = new Pattern("V0 T480 I[Piano] A4 A4 R A4 R G4 R A4 R D R A4 G4 R D R A4 R G4 R F4 R C R A4 R");
        Pattern m15 = new Pattern("V0 T480 I[Piano] G4 R F4 R D4 R E4 F4 R A4 R Cww R R R ");
        Pattern m16 = new Pattern("V0 T480 I[Piano] R R F4 D4 F4 G4 G#4 G4 F4 D4 G#4 G4 F4 D4 F4 G4 R R R R R R R R G#4 R");
        Pattern m17 = new Pattern("V0 T480 I[Piano] A4 C R A4 G#4 G4 F4 D4 E4 F4 R G4 R G#4 R C R R C# R G#4 R G#4 G4 F4 G4");
        Pattern m18 = new Pattern("V0 T480 I[Piano] R R R R R R R R F3 R G3 R A3 R F4 R E4 R R R D4 R R R E4 R");
        Pattern m19 = new Pattern("V0 T480 I[Piano] R R F4 R R R G4 R R R E4 R R R A4 R R R R R R R A4 G#4 G4 F#4");
        Pattern m20 = new Pattern("V0 T480 I[Piano] F4 E4 D#4 D4 C4 R R R R R R R D#4 R R R R R R R R R R R R R");
        Pattern m21 = new Pattern("V0 T480 I[Piano] R R F4 D4 F4 G4 G#4 G4 F4 D4 G#4 G4 F4 D4 E4 G4 R R R R R R R R G#4 R");
        Pattern m22 = new Pattern("V0 T480 I[Piano] A4 R C R A4 G#4 G4 F4 D4 E4 F4 R G4 R A4 R C R C# R G#4 R G#4 G4 F4 G4");
        Pattern m23 = new Pattern("V0 T480 I[Piano] R R R R R R F3 R G3 R A3 R F4 R E4 R R R D4 R R R E4 R R R");
        Pattern m24 = new Pattern("V0 T480 I[Piano] F4 R R R G4 R R R E4 R R R A4 R R R R R R R A4 G#4 G4 F#4 F4 E4");
        Pattern m25 = new Pattern("V0 T480 I[Piano] D#4 D4 C#4 R R R R R R R D4 R R R R R R R R R B3 R R R R R");
        //player.play(b,m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19,m20,m21,m22,m23,m24,m25);
        System.exit(0);
    }
}