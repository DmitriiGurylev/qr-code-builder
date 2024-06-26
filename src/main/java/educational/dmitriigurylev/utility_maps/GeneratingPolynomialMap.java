package educational.dmitriigurylev.utility_maps;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneratingPolynomialMap {
    private static final Map<Integer, int[]> map = new HashMap<>();
    static {
        map.put(7, new int[]{87, 229, 146, 149, 238, 102, 21});
        map.put(10, new int[]{251, 67, 46, 61, 118, 70, 64, 94, 32, 45});
        map.put(13, new int[]{74, 152, 176, 100, 86, 100, 106, 104, 130, 218, 206, 140, 78});
        map.put(15, new int[]{8, 183, 61, 91, 202, 37, 51, 58, 58, 237, 140, 124, 5, 99, 105});
        map.put(16, new int[]{120, 104, 107, 109, 102, 161, 76, 3, 91, 191, 147, 169, 182, 194, 225, 120});
        map.put(17, new int[]{43, 139, 206, 78, 43, 239, 123, 206, 214, 147, 24, 99, 150, 39, 243, 163, 136});
        map.put(18, new int[]{215, 234, 158, 94, 184, 97, 118, 170, 79, 187, 152, 148, 252, 179, 5, 98, 96, 153});
        map.put(20, new int[]{17, 60, 79, 50, 61, 163, 26, 187, 202, 180, 221, 225, 83, 239, 156, 164, 212, 212, 188, 190});
        map.put(22, new int[]{210, 171, 247, 242, 93, 230, 14, 109, 221, 53, 200, 74, 8, 172, 98, 80, 219, 134, 160, 105, 165, 231});
        map.put(24, new int[]{229, 121, 135, 48, 211, 117, 251, 126, 159, 180, 169, 152, 192, 226, 228, 218, 111, 0, 117, 232, 87, 96, 227, 21});
        map.put(26, new int[]{173, 125, 158, 2, 103, 182, 118, 17, 145, 201, 111, 28, 165, 53, 161, 21, 245, 142, 13, 102, 48, 227, 153, 145, 218, 70});
        map.put(28, new int[]{168, 223, 200, 104, 224, 234, 108, 180, 110, 190, 195, 147, 205, 27, 232, 201, 21, 43, 245, 87, 42, 195, 212, 119, 242, 37, 9, 123});
        map.put(30, new int[]{41, 173, 145, 152, 216, 31, 179, 182, 50, 48, 110, 86, 239, 96, 222, 125, 42, 173, 226, 193, 224, 130, 156, 37, 251, 216, 238, 40, 192, 180});
    }

    public static int[] getGeneratingPolynomial(int i) {
        return map.get(i);
    }
}


