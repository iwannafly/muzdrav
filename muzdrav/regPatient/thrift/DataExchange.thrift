namespace java ru.nkz.ivcgzo.regPatient.thrift

struct PatientInfoStruct{
	1:string fam,
	2:string im,
	3:string ot,
	4:i64 datar,
	5:string spolis,
	6:string npolis
}
struct Address{
	1:string city,
	2:string street,
	3:string house,
	4:string flat,
	5:string region
}
struct PatientAllStruct{
	1:i32 npasp,
	2:string fam,
	3:string im,
	4:string ot,
	5:i64 datar,
	6:string spolis,
	7:string npolis
	8:Address adpAddress,
	9:Address admAddress
}
struct Polis{
	1:i32 strg,
	2:string ser,
	3:string nom,
	4:i32 tdoc
}

/*patient*/
struct PatientPersonalInfoStruct{
	1:i32 npasp,
	2:string fam,
	3:string im,
	4:string ot,
	5:i64 datar,
	6:i32 pol,
	7:i32 jitel,
	8:i32 sgrp,
	9:Address adpAddress,
	10:Address admAddress
	11:Polis Polis_oms,
	12:Polis Polis_dms,
	13:string mrab,
	14:string namemr,
	15:i32 ncex,
	16:i32 cpol_pr,
	17:i32 terp,
	18:i32 tdoc,
	19:string docser,
	20:string docnum,
	21:i64 datadoc,
	22:string odoc,
	23:string snils,
	24:i64 dataz,
	25:string prof,
	26:string tel,
	27:i64 dsv,
	28:i32 prizn,
	29:i32 ter_liv,
	30:i32 region_liv
}
/*p_nambk*/
struct PatientNambkInfoStruct{
	1:i32 npasp,
	2:string nambk,
	3:i32 cpol,
	4:i64 datapr,
	5:i64 dataot,
	6:i32 ishod
}
/*сведения о представителе табл. p_preds*/
struct PatientAgentInfoStruct{
	1:i32 npasp,
	2:string fam,
	3:string im,
	4:string ot,
	5:i64 datar,
	6:i32 pol,
	7:string name_str,
	8:string ogrn_str,
	9:i32 vpolis,
	10:string spolis,
	11:string npolis,
	12:i32 tdoc,
	13:string docser,
	14:string docnum,
	15:string birthplace
}
/*pkov*/
struct PatientLgotaInfoStruct{
	1:i32 npasp,
	2:i32 lgota,
	3:i64 datau,
	4:string name,
	5:string kov,
	6:string pf
}
/*pkonti*/
struct PatientKontingentInfoStruct{
	1:i32 npasp,
	2:i32 kateg,
	3:i64 datau,
	4:string name
}
/*psign*/
struct PatientSignInfoStruct{
	1:i32 npasp,
	2:string grup,
	3:string ph,
	4:string allerg,
	5:string farmkol,
	6:string vitae,
	7:string vred
}
/*c_jalob*/
struct PatientJalobInfoStruct{
	1:i32 npasp,
	2:i32 id_gosp,
	3:i64 dataz,
	4:string jalob
}
/*c_gosp*/
struct PatientAllGospInfoStruct{
	1:i32 npasp,
	2:i32 ngosp,
	3:i32 id,
	4:i32 nist,
	5:i64 datap,
	6:i32 cotd,
	7:string diag_p,
	8:string named_p
}
/*c_gosp*/
struct PatientGospInfoStruct{
	1:i32 npasp,
	2:i32 ngosp,
	3:i32 id,
	4:i32 nist,
	5:i64 datap,
	6:string vremp,
	7:i32 pl_extr,
	8:string naprav,
	9:i32 n_org,
	10:i32 cotd,
	11:i32 sv_time,
	12:i32 sv_day,
	13:i32 ntalon,
	14:i32 vidtr,
	15:i32 pr_out,
	16:i32 alkg,
	17:i32 messr,
	18:i32 vid_trans,
	19:string diag_n,
	20:string diag_p,
	21:string named_n,
	22:string named_p,
	23:i32 nal_z,
	24:i32 nal_p,
	25:string toc,
	26:string ad,
	27:i64 smp_data,
	28:string smp_time,
	29:i32 smp_num,
	30:i32 cotd_p,
	31:i64 datagos,
	32:string vremgos,
	33:i32 cuser,
	34:i64 dataosm,
	35:string vremosm,
	36:i64 dataz
}
/*справочники*/
struct SpravStruct{
	1:i32 pcod,
	2:string name
}

service DataExchange {
	list<PatientAllStruct> getAllPatientInfo(1:PatientInfoStruct patient),
	PatientPersonalInfoStruct getPatientPersonalInfo(1:i32 npasp),
	PatientAgentInfoStruct getPatientAgentInfo(1:i32 npasp),
	list<PatientLgotaInfoStruct> getPatientLgotaInfo(1:i32 npasp),
	list<PatientKontingentInfoStruct> getPatientKontingentInfo(1:i32 npasp),
	PatientSignInfoStruct getPatientSignInfo(1:i32 npasp),
	PatientNambkInfoStruct getPatientNambkInfo(1:i32 npasp),
	list<PatientAllGospInfoStruct> getPatientAllGospInfo(1:i32 npasp, 2:i32 ngosp),
	PatientGospInfoStruct getPatientGospInfo(1:i32 npasp, 2:i32 ngosp),
	list<SpravStruct> getSpravInfo(1:string param), 

	i32 getAddPatient(1:PatientPersonalInfoStruct patinfo),
	void AddLgota(1:PatientLgotaInfoStruct lgota),
	void AddKont(1:PatientKontingentInfoStruct kont),
	void AddAgent(1:PatientAgentInfoStruct agent),
	void AddSign(1:PatientSignInfoStruct sign),
	void AddJalob(1:PatientJalobInfoStruct jalob),
	void AddGosp(1:PatientGospInfoStruct gosp),
	void AddNambk(1:PatientNambkInfoStruct nambk),

	void DeletePatient(1:i32 npasp),
	void DeleteNambk(1:i32 npasp, 2:i32 cpol),
	void DeleteLgota(1:i32 npasp, 2:i32 lgota),
	void DeleteKont(1:i32 npasp, 2:i32 kateg),
	void DeleteAgent(1:i32 npasp),
	void DeleteSign(1:i32 npasp),
	void DeleteJalob(1:i32 npasp, 2:i32 ngosp),
	void DeleteGosp(1:i32 npasp, 2:i32 ngosp),

	void UpdatePatient(1:PatientPersonalInfoStruct patinfo),
	void UpdateNambk(1:PatientNambkInfoStruct nambk),
	void UpdateLgota(1:i32 npasp, 2:i32 lgota),
	void UpdateKont(1:i32 npasp, 2:i32 kateg),
	void UpdateAgent(1:PatientAgentInfoStruct agent),
	void UpdateSign(1:PatientSignInfoStruct sign),
	void UpdateJalob(1:PatientJalobInfoStruct jalob),
	void UpdateGosp(1:PatientGospInfoStruct gosp)
}