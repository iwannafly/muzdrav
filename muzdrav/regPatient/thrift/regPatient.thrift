﻿namespace java ru.nkz.ivcgzo.thriftRegPatientinclude "../../../common/thrift/classifier.thrift"include "../../../common/thrift/kmiacServer.thrift"struct Address{	1:string region,	2:string city,	3:string street,	4:string house,	5:string flat	}struct PatientBrief{	1:optional i32 npasp,	2:string fam,	3:string im,	4:string ot,	5:optional i64 datar,	6:string spolis,	7:string npolis,	8:Address adpAddress,	9:Address admAddress}struct Polis{	1:i32 strg,	2:string ser,	3:string nom,	4:i32 tdoc}/*p_nambk*/struct Nambk{	1:i32 npasp,	2:string nambk,	3:i32 nuch,	4:i32 cpol,	5:i64 datapr,	6:i64 dataot,	7:i32 ishod}/*patient*/struct PatientFullInfo{	1:i32 npasp,	2:string fam,	3:string im,	4:string ot,	5:i64 datar,	6:i32 pol,	7:i32 jitel,	8:i32 sgrp,	9:string mrab,	10:string namemr,	11:i32 ncex,	12:i32 cpol_pr,	13:i32 terp,	14:i32 tdoc,	15:string docser,	16:string docnum,	17:i64 datadoc,	18:string odoc,	19:string snils,	20:i64 dataz,	21:string prof,	22:string tel,	23:i64 dsv,	24:i32 prizn,	25:i32 ter_liv,	26:i32 region_liv,	27:Nambk nambk	28:Address adpAddress,	29:Address admAddress	30:Polis polis_oms,	31:Polis polis_dms,}/*сведения о представителе табл. p_preds*/struct Agent{	1:i32 npasp,	2:string fam,	3:string im,	4:string ot,	5:i64 datar,	6:i32 pol,	7:string name_str,	8:string ogrn_str,	9:i32 vpolis,	10:string spolis,	11:string npolis,	12:i32 tdoc,	13:string docser,	14:string docnum,	15:string birthplace}/*pkov*/struct Lgota{	1:i32 id,	2:i32 npasp,	3:i32 lgota,	4:i64 datau,	5:string name,	6:string kov,	7:string pf}/*pkonti*/struct Kontingent{	1:i32 id,	2:i32 npasp,	3:i32 kateg,	4:i64 datau,	5:string name}/*psign*/struct Sign{	1:i32 npasp,	2:string grup,	3:string ph,	4:string allerg,	5:string farmkol,	6:string vitae,	7:string vred}/*c_jalob*/struct Jalob{	1:i32 id,	2:i32 id_gosp,	3:i64 dataz,	4:string jalob}/*c_gosp*/struct AllGosp{	1:i32 id,	2:i32 ngosp,	3:i32 npasp,	4:i32 nist,	5:i64 datap,	6:i32 cotd,	7:string diag_p,	8:string named_p}/*c_gosp*/struct Gosp{	1:i32 id,	2:i32 ngosp,	3:i32 npasp,	4:i32 nist,	5:i64 datap,	6:string vremp,	7:i32 pl_extr,	8:string naprav,	9:i32 n_org,	10:i32 cotd,	11:i32 sv_time,	12:i32 sv_day,	13:i32 ntalon,	14:i32 vidtr,	15:i32 pr_out,	16:i32 alkg,	17:bool messr,	18:i32 vid_trans,	19:string diag_n,	20:string diag_p,	21:string named_n,	22:string named_p,	23:bool nal_z,	24:bool nal_p,	25:string toc,	26:string ad,	27:i64 smp_data,	28:string smp_time,	29:i32 smp_num,	30:i32 cotd_p,	31:i64 datagos,	32:string vremgos,	33:i32 cuser,	34:i64 dataosm,	35:string vremosm,	36:i64 dataz}/*справочники*/struct SpravStruct{	1:i32 pcod,	2:string name}/** * Пациент с такими данными не найден. */exception PatientNotFoundException {}/** * Представитель с такими данными не найден */exception AgentNotFoundException {}/** * Запись льготы с такими данными не найдена */exception LgotaNotFoundException {}/** * Запись категории с такими данными не найдена */exception KontingentNotFoundException {}/** * Особая информация о пациенте с такими данными не найдены */exception SignNotFoundException {}/** * Запись госпитализации пациента с такими данными не найдена */exception GospNotFoundException {}/** * Жалобы пациента с такими данными не найдены */exception JalobNotFoundException {}/** * Пациент с такими данными уже существует */exception PatientAlreadyExistException {}/** * Пациент с такими данными уже существует */exception LgotaAlreadyExistException {}/** * Пациент с такими данными уже существует */exception KontingentAlreadyExistException {}/** * Пациент с такими данными уже существует */exception AgentAlreadyExistException {}/** * Пациент с такими данными уже существует */exception SignAlreadyExistException {}/** * Пациент с такими данными уже существует */exception JalobAlreadyExistException {}/** * Пациент с такими данными уже существует */exception GospAlreadyExistException {}/** * Пациент с такими данными уже существует */exception NambkAlreadyExistException {}service ThriftRegPatient extends kmiacServer.KmiacServer {	list<PatientBrief> getAllPatientBrief(1:PatientBrief patient) throws (1: PatientNotFoundException pnfe),	PatientFullInfo getPatientFullInfo(1:i32 npasp) throws (1: PatientNotFoundException pnfe),	Agent getAgent(1:i32 npasp) throws (1: AgentNotFoundException anfe),	list<Lgota> getLgota(1:i32 npasp) throws (1: LgotaNotFoundException lnfe),	list<Kontingent> getKontingent(1:i32 npasp) throws (1: KontingentNotFoundException knfe),	Sign getSign(1:i32 npasp) throws (1: SignNotFoundException snfe),	list<AllGosp> getAllGosp(1:i32 npasp) throws (1: GospNotFoundException gnfe),	Gosp getGosp(1:i32 id) throws (1: GospNotFoundException gnfe),	list<Jalob> getAllJalob(1:i32 idGosp) throws (1: JalobNotFoundException jnfe),	list<SpravStruct> getSpravInfo(1:string param), 	i32 addPatient(1:PatientFullInfo patinfo) throws (1:PatientAlreadyExistException paee),	i32 addLgota(1:Lgota lgota) throws (1:LgotaAlreadyExistException laee),	i32 addKont(1:Kontingent kont) throws (1:KontingentAlreadyExistException kaee),	void addAgent(1:Agent agent) throws (1:AgentAlreadyExistException aaee),	void addSign(1:Sign sign) throws (1:SignAlreadyExistException saee),	i32 addJalob(1:Jalob jalob) throws (1:JalobAlreadyExistException jaee),	i32 addGosp(1:Gosp gosp) throws (1:GospAlreadyExistException gaee),	void addNambk(1:Nambk nambk) throws (1:NambkAlreadyExistException naee),	void deletePatient(1:i32 npasp),	void deleteNambk(1:i32 npasp, 2:i32 cpol),	void deleteLgota(1:i32 id),	void deleteKont(1:i32 id),	void deleteAgent(1:i32 npasp),	void deleteSign(1:i32 npasp),	void deleteJalob(1:i32 npasp, 2:i32 ngosp),	void deleteGosp(1:i32 npasp, 2:i32 ngosp),	void updatePatient(1:PatientFullInfo patinfo),	void updateNambk(1:Nambk nambk),	void updateLgota(1:Lgota lgota),	void updateKont(1:Kontingent kont),	void updateAgent(1:Agent agent),	void updateSign(1:Sign sign),	void updateJalob(1:Jalob jalob),	void updateGosp(1:Gosp gosp)}