
echo "" 1>/dev/null
clear
HOME_DIR=`pwd`

generateJavaSource() {
  cd $HOME_DIR/$1
  echo "Executing script `pwd`/gen.sh"
  sh gen.sh || echo "failed execution `pwd`/gen.sh"
  cd $HOME_DIR  
}

generateJavaSource common/thrift
generateJavaSource muzdrav/auth/thrift
generateJavaSource muzdrav/lds/thrift
generateJavaSource muzdrav/mss/thrift
generateJavaSource muzdrav/osm/thrift
generateJavaSource muzdrav/regPatient/thrift
generateJavaSource muzdrav/admin/thrift
generateJavaSource muzdrav/viewselect/thrift
generateJavaSource muzdrav/genTalons/thrift
generateJavaSource muzdrav/outputInfo/thrift
generateJavaSource muzdrav/genReestr/thrift
generateJavaSource muzdrav/hospital/thrift
generateJavaSource muzdrav/Vgr/thrift
generateJavaSource muzdrav/kartaRInv/thrift
generateJavaSource muzdrav/disp/thrift
generateJavaSource muzdrav/infomat/thrift
generateJavaSource muzdrav/print/thrift
