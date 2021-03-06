import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.Color;
import javax.imageio.ImageIO;

public class DecryptImage {

    Color[] colors;
    int x;
    int y;

    public DecryptImage(BufferedImage cipherImage, String saveAs) throws IOException {
        parseImage(cipherImage);
        decode(saveAs);
        }

    public DecryptImage(File file, String saveAs) throws IOException {
        this(ImageIO.read(file), saveAs);
    }
    

    private void parseImage(BufferedImage image) throws IOException {
        this.x = image.getWidth();
        this.y = image.getHeight();

        this.colors = new Color[this.x*this.y];
        System.out.println(this.colors.length);
        for (int y = 0; y< this.y; y++) {
            for (int x = 0; x <this.x; x++) {
                int clr = image.getRGB(x, y);
                colors[y*this.x + x] = new Color(clr);
            }
        }
    }

    // Decodes the image, saving it in a file at outputText/filename.txt
    private void decode(String filename) throws FileNotFoundException {
        int sum = 0;
        PrintWriter out = new PrintWriter("outputText/" + filename + ".txt");

        for (int i = 0; i< this.x; i++) {
            Color curr = colors[i];
            sum += curr.getRed()*i + curr.getGreen()*i + curr.getBlue()*i;
        }

        int span = sum % this.x; 
        System.out.println("Span is " + (sum%this.x));

        int index = this.x;
        int currSum = 0;
        
        while(index < this.colors.length) {
            // if (index%10000 == 0) {
            //     System.out.println(index);
            // }
            if((index-this.x) %span == 0 && (index - this.x) != 0) {
                int val = currSum%256;
                out.write((char)val);
                currSum = 0;
            }
            Color curColor = colors[index];
            currSum += 25*curColor.getRed() + 5*curColor.getGreen() + 1*curColor.getBlue();
            index++;
        }
    }

    public static void main(String args[]) throws IOException {
        File file = new File("outputImages/EncryptedwhiteA.png");
        BufferedImage bi = ImageIO.read(file);
        DecryptImage di = new DecryptImage(bi, "aaaa");
    }
}
