package com.example.anonymous.cikgood.rests;

import com.example.anonymous.cikgood.response.ResponGuruDetail;
import com.example.anonymous.cikgood.response.ResponseCariGuru;
import com.example.anonymous.cikgood.response.ResponseCreatePemesanan;
import com.example.anonymous.cikgood.response.ResponseDataMatpel;
import com.example.anonymous.cikgood.response.ResponseGuru;
import com.example.anonymous.cikgood.response.ResponseGuruDataMatpel;
import com.example.anonymous.cikgood.response.ResponseJadwal;
import com.example.anonymous.cikgood.response.ResponseKabupaten;
import com.example.anonymous.cikgood.response.ResponseLogin;
import com.example.anonymous.cikgood.response.ResponseLokasi;
import com.example.anonymous.cikgood.response.ResponseMatpel;
import com.example.anonymous.cikgood.response.ResponseCreateMurid;
import com.example.anonymous.cikgood.response.ResponseMengajar;
import com.example.anonymous.cikgood.response.ResponsePendidikan;
import com.example.anonymous.cikgood.response.ResponsePengalamanKerja;
import com.example.anonymous.cikgood.response.ResponseSaldo;
import com.example.anonymous.cikgood.response.ResponseTingkatan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("matpel/find-all")
    Call<ResponseMatpel> matpelFindAll();

    @GET("guru/find-all")
    Call<ResponseGuru> guruFindAll();

    @GET("guru/find-by-id")
    Call<ResponGuruDetail> guruFindById(@Query("id") int id);

    @GET("saldo/find-by-id")
    Call<ResponseSaldo> saldoFindById(@Query("id") int id);

    @FormUrlEncoded
    @POST("murid/create")
    Call<ResponseCreateMurid> simpanMurid(@Field("nama") String nama,
                                          @Field("email") String email,
                                          @Field("no_hp") String no_hp,
                                          @Field("password") String password);

    @FormUrlEncoded
    @POST("murid/update")
    Call<ResponseCreateMurid> updateMurid(@Field("id") Integer id,
                                          @Field("nama") String nama,
                                          @Field("no_hp") String no_hp,
                                          @Field("email") String email,
                                          @Field("alamat") String alamat,
                                          @Field("jk") String jk,
                                          @Field("nisn") String nisn,
                                          @Field("kelas") String kelas,
                                          @Field("nama_sekolah") String nama_sekolah,
                                          @Field("photo") String photo);

    @FormUrlEncoded
    @POST("login/murid")
    Call<ResponseLogin> muridLogin(
            @Field("email") String email,
            @Field("password") String password);

    @GET("tingkatan/find-all")
    Call<ResponseTingkatan> tingkatanFindAll();

    @GET("data-matpel/find-by-id-tingkatan")
    Call<ResponseDataMatpel> dataMatpelFindByIdTingkatan(@Query("id") int tingkatan);

    @GET("kota/find-all")
    Call<ResponseKabupaten> getDataKabupaten();

    @GET("data-matpel/cari-guru")
    Call<ResponseCariGuru> cariGuru(@Query("tingkatan") int tingkatan,
                                    @Query("matpel") int matpel,
                                    @Query("kota") String kota);
    @GET("data-matpel/cari-guru")
    Call<ResponseCariGuru> dapatGuru(@Query("tingkatan") int tingkatan,
                                    @Query("matpel") int matpel,
                                    @Query("kota") String kota);

    @GET("data-matpel/find-pengalaman-kerja")
    Call<ResponsePengalamanKerja> pengalamanKerja(@Query("id") int guru_id);

    @GET("lokasi/find-by-id")
    Call<ResponseLokasi> getDataLokasi(@Query("id") int guru_id);

    @GET("pendidikan/find-by-id")
    Call<ResponsePendidikan> getDataPendidikan(@Query("id") int guru_id);

    @GET("mengajar/find-by-id")
    Call<ResponseMengajar> getDataMengajar(@Query("id") int guru_id);

    @GET("guru-jadwal/find-by-id")
    Call<ResponseJadwal> getDataJadwal(@Query("id") int guru_id);

    @GET("data-matpel/find")
    Call<ResponseGuruDataMatpel> getGuruDataMatpel(@Query("id") int guru_id);

    /* Data Pemesanan */

    // create data pemesanan
    @FormUrlEncoded
    @POST("pemesanan/create")
    Call<ResponseCreatePemesanan> simpanPemesanan ( @Field("guru_id") int guru_id,
                                                    @Field("murid_id") int murid_id,
                                                    @Field("matpel") String matpel,
                                                    @Field("jumlah_pertemuan") int jumlah_pertemuan,
                                                    @Field("durasi") double durasi,
                                                    @Field("alamat") String alamat,
                                                    @Field("lat") double lat,
                                                    @Field("lng") double lng,
                                                    @Field("jadwal") String jadwal,
                                                    @Field("pesan_tambahan") String pesan_tambahan,
                                                    @Field("harga") double harga,
                                                    @Field("harga_total") double harga_total
                                                   );

    @FormUrlEncoded
    @POST("pemesanan/create2")
    Call<ResponseCreatePemesanan> simpanPemesanan2 ( @Field("guru_id") int guru_id,
                                                     @Field("murid_id") int murid_id,
                                                     @Field("matpel") String matpel,
                                                     @Field("jumlah_pertemuan") int jumlah_pertemuan,
                                                     @Field("durasi") double durasi

    );
//    @FormUrlEncoded
//    @POST("pemesanan/create")
//    Call<ResponseCreatePemesanan> simpanPemesanan( @Field("guru_id") int guru_id,
//                                                   @Field("murid_id") int murid_id,
//                                                   @Field("matpel") String matpel,
//                                                   @Field("jumlah_pertemuan") int jumlah_pertemuan,
//                                                   @Field("durasi") double durasi,
//                                                   @Field("alamat") String alamat,
//                                                   @Field("lat") double lat,
//                                                   @Field("lng") double lng,
//                                                   @Field("jadwal") String jadwal,
//                                                   @Field("pesan_tambahan") String pesan_tambahan,
//                                                   @Field("harga") double harga,
//                                                   @Field("harga_total") double harga_total
//                                                );


}
