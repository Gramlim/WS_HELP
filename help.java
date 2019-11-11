implementation 'com.squareup.retrofit2:retrofit:2.3.0'
        implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
        implementation 'com.squareup.retrofit2:converter-simplexml:2.3.0'
        implementation 'com.squareup.okhttp3:okhttp:4.1.0'

////////////////////////////////////////////////////////////////////////////

public class CustomAdapter extends ArrayAdapter<Valuet> {
    private final Activity context;
    private final List<Valuet> listValuet;
    public CustomAdapter(Activity context, List<Valuet> valuet) {
        super(context, R.layout.valuet_item, valuet);
        this.listValuet = valuet;
        this.context = context;
    }
    static class ViewHolder {
        public TextView name;
        public TextView charcode;
        public TextView value;

        ViewHolder(View view) {
            this.name = view.findViewById(R.id.name_txt);
            this.charcode = view.findViewById(R.id.code_txt);
            this.value = view.findViewById(R.id.value_txt);
        }

        void setData (Valuet valuet){
            this.name.setText(valuet.getName());
            this.charcode.setText(valuet.getCharCode());
            this.value.setText(valuet.getValue());
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View rowView;
        LayoutInflater inflater = context.getLayoutInflater();
        rowView = inflater.inflate(R.layout.valuet_item, null, true);
        holder = new ViewHolder(rowView);
        holder.setData(listValuet.get(position));
        return rowView;
    }

///////////////////////////////////////////////////////////////////////////////////

    public class ServiceGenerator {
        public static <S> S createService(Class<S> serviceClass, String username, String password) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://intelligent-system.online/")
                    .addConverterFactory(GsonConverterFactory.create());

            String authToken = Credentials.basic(username, password);

            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor);

            builder.client(httpClient.build());

            Retrofit retrofit = builder.build();
            return retrofit.create(serviceClass);
        }

        public static <S> S createService(Class<S> serviceClass, String authToken){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://intelligent-system.online/")
                    .addConverterFactory(GsonConverterFactory.create());

            AuthenticationInterceptor interceptor = new AuthenticationInterceptor("Bearer " + authToken);

            Log.d("request", interceptor.toString());
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor);

            builder.client(httpClient.build());
            Retrofit retrofit = builder.build();
            return retrofit.create(serviceClass);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////

    public class AuthenticationInterceptor implements Interceptor {
        private String authTocken;

        public AuthenticationInterceptor(String token){
            this.authTocken = token;
        }
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException{
            Request original = chain.request();
            Request.Builder builder = original.newBuilder().header("Authorization",authTocken);

            Request request = builder.build();
            return chain.proceed(request);


        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    page = Integer.valueOf(String.valueOf(p.getText()));
    per_page = Integer.valueOf(String.valueOf(pp.getText()));

    Call<Events> call = tokenService.getEvent(new MetaEvents(String.valueOf(page), String.valueOf(per_page)));

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    Обьявление
    public static final String APP_PREFERENCES = "Tockens";
    public static final String APP_PREFERENCES_TOKEN1 = "token1";
    SharedPreferences mToken;

    Запись
    SharedPreferences sharedPrefences = MainActivity.this.getSharedPreferences
            ("Tokens",0);
    SharedPreferences.Editor editor = sharedPrefences.edit();
editor.putString("token1", response.body().getToken());
editor.apply();
    Вытаскивание
    SharedPreferences sharedPrefences = getSharedPreferences("Tokens",0);
    String token = sharedPrefences.getString("token1", "fail");