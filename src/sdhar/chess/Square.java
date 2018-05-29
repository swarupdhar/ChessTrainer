package sdhar.chess;

public enum Square {

    A8(0), B8(1), C8(2), D8(3), E8(4), F8(5), G8(6), H8(7),
    A7(8), B7(9), C7(10), D7(11), E7(12), F7(13), G7(14), H7(15),
    A6(16), B6(17), C6(18), D6(19), E6(20), F6(21), G6(22), H6(23),
    A5(24), B5(25), C5(26), D5(27), E5(28), F5(29), G5(30), H5(31),
    A4(32), B4(33), C4(34), D4(35), E4(36), F4(37), G4(38), H4(39),
    A3(40), B3(41), C3(42), D3(43), E3(44), F3(45), G3(46), H3(47),
    A2(48), B2(49), C2(50), D2(51), E2(52), F2(53), G2(54), H2(55),
    A1(56), B1(57), C1(58), D1(59), E1(60), F1(61), G1(62), H1(63),
    NONE(-1);



    final int index;

    Square(final int index) {
        this.index = index;
    }

    public int toIndex() { return index; }

    public static Square fromIndex(final int index) {
        if (index < 0 || index >= 64) return NONE;
        switch (index) {
            case 0:
                return Square.A8;
            case 1:
                return Square.B8;
            case 2:
                return Square.C8;
            case 3:
                return Square.D8;
            case 4:
                return Square.E8;
            case 5:
                return Square.F8;
            case 6:
                return Square.G8;
            case 7:
                return Square.H8;

            case 8:
                return Square.A7;
            case 9:
                return Square.B7;
            case 10:
                return Square.C7;
            case 11:
                return Square.D7;
            case 12:
                return Square.E7;
            case 13:
                return Square.F7;
            case 14:
                return Square.G7;
            case 15:
                return Square.H7;

            case 16:
                return Square.A6;
            case 17:
                return Square.B6;
            case 18:
                return Square.C6;
            case 19:
                return Square.D6;
            case 20:
                return Square.E6;
            case 21:
                return Square.F6;
            case 22:
                return Square.G6;
            case 23:
                return Square.H6;

            case 24:
                return Square.A5;
            case 25:
                return Square.B5;
            case 26:
                return Square.C5;
            case 27:
                return Square.D5;
            case 28:
                return Square.E5;
            case 29:
                return Square.F5;
            case 30:
                return Square.G5;
            case 31:
                return Square.H5;

            case 32:
                return Square.A4;
            case 33:
                return Square.B4;
            case 34:
                return Square.C4;
            case 35:
                return Square.D4;
            case 36:
                return Square.E4;
            case 37:
                return Square.F4;
            case 38:
                return Square.G4;
            case 39:
                return Square.H4;

            case 40:
                return Square.A3;
            case 41:
                return Square.B3;
            case 42:
                return Square.C3;
            case 43:
                return Square.D3;
            case 44:
                return Square.E3;
            case 45:
                return Square.F3;
            case 46:
                return Square.G3;
            case 47:
                return Square.H3;

            case 48:
                return Square.A2;
            case 49:
                return Square.B2;
            case 50:
                return Square.C2;
            case 51:
                return Square.D2;
            case 52:
                return Square.E2;
            case 53:
                return Square.F2;
            case 54:
                return Square.G2;
            case 55:
                return Square.H2;

            case 56:
                return Square.A1;
            case 57:
                return Square.B1;
            case 58:
                return Square.C1;
            case 59:
                return Square.D1;
            case 60:
                return Square.E1;
            case 61:
                return Square.F1;
            case 62:
                return Square.G1;
            default:
                return Square.H1;
        }
    }
}
