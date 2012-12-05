namespace java ru.nkz.ivcgzo.thriftCommon.classifier

/**
 * Класс, состоящий из двух полей: код и название, где код - число.
 */
struct IntegerClassifier {
	 1: i32 pcod;
	 2: string name;
}

/**
 * Класс, состоящий из двух полей: код и название, где код - строка.
 */
struct StringClassifier {
	 1: string pcod;
	 2: string name;
}

enum IntegerClassifiers {
	n_aba = 1,
	n_abb = 2,
	n_abc = 3,
	n_abd = 4,
	n_abg = 5,
	n_abs = 6,
	n_abt = 7,
	n_abv = 8,
	n_abx = 9,
	n_aby = 10,
	n_af0 = 11,
	n_ai0 = 12,
	n_al0 = 13,
	n_alk = 14,
	n_am0 = 15,
	n_ao0 = 16,
	n_ap0 = 17,
	n_aq0 = 18,
	n_arez = 19,
	n_az0 = 20,
	n_az9 = 21,
	n_azt = 22,
	n_d0s = 23,
	n_db1 = 24,
	n_db2 = 25,
	n_db3 = 26,
	n_db4 = 27,
	n_db5 = 28,
	n_db7 = 30,
	n_db8 = 31,
	n_edd = 32,
	n_f008 = 33,
	n_frm = 34,
	n_frw = 35,
	n_grup = 36,
	n_kas = 37,
	n_l00 = 38,
	n_l01 = 39,
	n_l02 = 40,
	n_lds = 41,
	n_lkn = 42,
	n_lkr = 43,
	n_m00 = 44,
	n_med = 45,
	n_mnn = 46,
	n_n00 = 47,
	n_o00 = 48,
	n_opl = 49,
	n_p07 = 50,
	n_p0e1 = 51,
	n_p0s = 52,
	n_priznd = 53,
	n_r0l = 54,
	n_rzd = 55,
	n_t00 = 56,
	n_u00 = 57,
	n_u10 = 58,
	n_u50 = 59,
	n_vdi = 60,
	n_vgo = 61,
	n_vp2 = 62,
	n_vp3 = 63,
	n_vtr = 64,
	n_w04 = 65,
	n_w07 = 66,
	n_z00 = 67,
	n_z01 = 68,
	n_z10 = 69,
	n_z30 = 70,
	n_z42 = 71,
	n_z43 = 72,
	n_z60 = 73,
	n_z70 = 74,
	n_z80 = 75,
	n_z90 = 76,
	n_z11 = 77,
	n_spec = 78,
	n_din = 79,
	n_p0c = 80,
	n_v10 = 81,
	n_v0a = 82,
	n_v0b = 83,
	n_v0c = 84,
	n_v0d = 85,
	n_v0e = 86,
	n_v0f = 87,
	n_v0g = 88,
	n_v0h = 89,
	n_v0m = 90,
	n_v0n = 91,
	n_v0p = 92,
	n_v0r = 93,
	n_v0s = 94,
	n_v0t = 95,
	n_az51 = 96,
	n_bz5 = 97,
	n_def = 98,
	n_etp = 99,
	n_kderr = 100,
	n_nsilpu = 101,
	n_ppl = 102,
	n_shablon = 103,
	n_smorf = 104,
	n_svl = 105,
	n_tip = 106,
	n_vp1 = 107,
	n_vr_doc = 108,
	n_z43_gr = 109, 
	n_period = 110,
        n_db10 = 111,
        n_db11 = 112,
        n_db12 = 113,
        n_db13 = 114

}

enum StringClassifiers {
	n_a00 = 1,
	n_azj = 2,
	n_b00 = 3,
	n_c00 = 4,
	n_db9 = 5,
	n_k02 = 6,
	n_ldi = 7,
	n_nz1 = 8,
	n_obst = 9,
	n_r0c = 11,
	n_r0z = 12,
	n_s00 = 13,
	n_spr = 14,
	n_db6 = 15,
	n_messtet = 16,
	n_nsi_obst = 17,
	n_nz2 = 18,
	n_s0m = 19
}

enum ClassifierSortOrder {
	none = 0,
	ascending = 1,
	descending = 2
}

enum ClassifierSortFields {
	pcod = 1,
	name = 2,
	pcodName = 3
}
