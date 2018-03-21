package sdhar.chess;

public enum Square {

    A8(0), B8(1), C8(2), D8(3), E8(4), F8(5), G8(6), H8(7),
    A7(8), B7(9), C7(10), D7(11), E7(12), F7(13), G7(14), H7(15),
    A6(16), B6(17), C6(18), D6(19), E6(20), F6(21), G6(22), H6(23),
    A5(24), B5(25), C5(26), D5(27), E5(28), F5(29), G5(30), H5(31),
    A4(32), B4(33), C4(34), D4(35), E4(36), F4(37), G4(38), H4(39),
    A3(40), B3(41), C3(42), D3(43), E3(44), F3(45), G3(46), H3(47),
    A2(48), B2(49), C2(50), D2(51), E2(52), F2(53), G2(54), H2(55),
    A1(56), B1(57), C1(58), D1(59), E1(60), F1(61), G1(62), H1(63);



    final int index;

    Square(final int index) {
        this.index = index;
    }

//    public static Square fromIndex(final int index) {
//        if (index < 0 || index >= 64) return null;
//        switch (index) {
//            case 0: return A8;
//            case 1: return B8;
//            case 2: return C8;
//            case 3: return D8;
//            case 4: return E8;
//            case 5: return F8;
//            case 6: return G8;
//            case 7: return H8;
//
//            case 8: return A7;
//            case 9: return B7;
//            case 10: return C7;
//            case 11: return D7;
//            case 12: return E7;
//            case 13: return F7;
//            case 14: return G7;
//            case 15: return H7;
//
//            case 16: return A6;
//            case 17: return B6;
//            case 18: return C6;
//            case 19: return D6;
//            case 20: return E6;
//            case 21: return F6;
//            case 22: return G6;
//            case 23: return H6;
//
//            case 24: return A5;
//            case 25: return B5;
//            case 26: return C5;
//            case 27: return D5;
//            case 28: return E5;
//            case 29: return F5;
//            case 30: return G5;
//            case 31: return H5;
//
//            case 32: return A4;
//            case 33: return B4;
//            case 34: return C4;
//            case 35: return D4;
//            case 36: return E4;
//            case 37: return F4;
//            case 38: return G4;
//            case 39: return H4;
//
//            case 40: return A3;
//            case 41: return B3;
//            case 42: return C3;
//            case 43: return D3;
//            case 44: return E3;
//            case 45: return F3;
//            case 46: return G3;
//            case 47: return H3;
//
//            case 48: return A3;
//            case 49: return B2;
//            case 50: return C2;
//            case 51: return D2;
//            case 52: return E2;
//            case 53: return F2;
//            case 54: return G2;
//            case 55: return H2;
//
//            case 56: return A1;
//            case 57: return B1;
//            case 58: return C1;
//            case 59: return D1;
//            case 60: return E1;
//            case 61: return F1;
//            case 62: return G1;
//            default: return H1;
//        }
//    }
}
