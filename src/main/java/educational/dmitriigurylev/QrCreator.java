package educational.dmitriigurylev;

import educational.dmitriigurylev.customExceptions.UnknownEncodingTypeException;
import educational.dmitriigurylev.encoders.IntLetterEncoder;
import educational.dmitriigurylev.encoders.IntegerEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class QrCreator {
    QrCodeField qrCodeField;

    public int[][] createQr() {
        qrCodeField = new QrCodeField(Version.ONE);
        qrCodeField.addFinderPatterns();
        qrCodeField.addSynchronizationLines();
        qrCodeField.addTypeInformationBits();

        Object objectToEncode = "";

        int[] decimalArr;
        if (objectToEncode.getClass() == String.class) {
            decimalArr = IntLetterEncoder.encodeSymbols((String) objectToEncode);
        } else if (objectToEncode.getClass() == Integer.class) {
            decimalArr = IntegerEncoder.encodeInteger((int) objectToEncode);
        } else if (objectToEncode.getClass() == FileInputStream.class) {
//            decimalArr = ByteEncoder.encodeBytes(objectToEncode);
            throw new RuntimeException("it's not ready yet");
        } else {
            throw new UnknownEncodingTypeException("You can encode digits/letters/bytes only");
        }

        int[] generatingPolynomial = GeneratingPolynomial.map.get(17);

        List<Integer> listCorrectBytes = new LinkedList<>(Arrays.stream(decimalArr).boxed().collect(Collectors.toList()));
        while (listCorrectBytes.size() < generatingPolynomial.length) {
            listCorrectBytes.add(0);
        }
        int[] cValueArr = new int[generatingPolynomial.length];
        int[] dValueArr = new int[generatingPolynomial.length];

        for (int count = 0; count < generatingPolynomial.length; count++) {
            int aValue = listCorrectBytes.remove(0);
            int bValue = AB_Map.getMapInstance().getVal(aValue);
            if (listCorrectBytes.size() < generatingPolynomial.length) {
                listCorrectBytes.add(0);
            }

            for (int i = 0; i < cValueArr.length; i++) {
                cValueArr[i] = (generatingPolynomial[i] + bValue) % 255;
                dValueArr[i] = CD_Map.getMapInstance().getVal(cValueArr[i]);
                listCorrectBytes.set(i, dValueArr[i] ^ listCorrectBytes.get(i));
            }
        }

        String[] unitedArr = new String[decimalArr.length + listCorrectBytes.size()];
        for (int i = 0; i < decimalArr.length; i++) {
            unitedArr[i] = String.valueOf(decimalArr[i]);
        }
        for (int i = decimalArr.length; i < unitedArr.length; i++) {
            unitedArr[i] = String.valueOf(listCorrectBytes.remove(0));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < unitedArr.length; i++) {
            int decimalValue = Integer.parseInt(unitedArr[i]);
            String binStr = Integer.toBinaryString(decimalValue);
            unitedArr[i] = String.format("%8s", binStr).replace(' ', '0');
            sb.append(unitedArr[i]);
        }
        encodeBitsSequence(sb);
        applyMaskPattern();
        drawImage(qrCodeField.getField());
        return new int[1][1];
    }

    private void applyMaskPattern() {
        for (int x=0; x<qrCodeField.getField()[0].length; x++) {
            if (x % 3 == 0) {
                for (int y = 0; y < qrCodeField.getField().length; y++) {
                    if (qrCodeField.getField()[y][x].isBusy()) {
                        continue;
                    }
                    qrCodeField.getField()[y][x].setValue(qrCodeField.getField()[y][x].getValue() == 1 ? 0 : 1);
                }
            }
        }
    }

    private void encodeBitsSequence(StringBuilder sb) {
        Cell[][] f = qrCodeField.getField();
        Direction dir = Direction.up;
        int x = f[0].length - 1;
        int y = f.length - 1;

        while (!sb.isEmpty()) {
            while (dir == Direction.up) {
                if (!f[y][x].isBusy()) {
                    f[y][x].setValue(sb.charAt(0) == '0' ? 0 : 1);
                    sb.deleteCharAt(0);
                    drawImage(f);
                }
                x--;

                if (!f[y][x].isBusy()) {
                    f[y][x].setValue(sb.charAt(0) == '0' ? 0 : 1);
                    sb.deleteCharAt(0);
                    drawImage(f);
                }
                x++;
                y--;
                if (y < 0) {
                    dir = Direction.down;
                    y++;
                    x-=2;
                    if (x == 6) {
                        x--;
                    }
                }
            }

            while (dir == Direction.down) {
                if (!f[y][x].isBusy()) {
                    f[y][x].setValue(sb.charAt(0) == '0' ? 0 : 1);
                    sb.deleteCharAt(0);
                    drawImage(f);
                }
                x--;

                if (!f[y][x].isBusy()) {
                    f[y][x].setValue(sb.charAt(0) == '0' ? 0 : 1);
                    sb.deleteCharAt(0);
                    drawImage(f);
                }
                x++;
                y++;
                if (y >= f.length) {
                    dir = Direction.up;
                    y--;
                    x-=2;
                    if (x == 6) {
                        x--;
                    }
                }
            }
        }
    }

    private void drawImage(Cell[][] field) {
        BufferedImage qrImage = new BufferedImage(
                field[0].length+4,
                field.length+4,
                BufferedImage.TYPE_BYTE_GRAY);

        for (int x = 0; x < field[0].length+4; x++) {
            if (x == 0 || x == 1 || x == field[0].length+2 || x == field[0].length+3) {
                for (int y = 2; y < field.length+2; y++) {
                    qrImage.setRGB(x, y, 0xFFFFFF);
                }
            }
            qrImage.setRGB(x, 0, 0xFFFFFF);
            qrImage.setRGB(x, 1, 0xFFFFFF);
            qrImage.setRGB(x, field.length+2, 0xFFFFFF);
            qrImage.setRGB(x, field.length+3, 0xFFFFFF);
        }

        for (int x = 2; x < field[0].length+2; x++) {
            for (int y = 2; y < field.length+2; y++) {
                qrImage.setRGB(x, y, field[y-2][x-2].getValue() == 0 ? 0xFFFFFF : 0x000000);
            }
        }
        File outputQrImageFile = new File("qr_image.jpg");
        try {
            ImageIO.write(qrImage, "jpg", outputQrImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    enum Direction {up, down}

}
