import pandas as pd

df = pd.read_csv('d:/data/final/real/horse_total_all_20201218.csv')

del df['Unnamed: 0']
#del df['rcdate']
#del df['rcno']

df['sex'] = df['sex'].replace("암",1)
df['sex'] = df['sex'].replace("수",2)
df['sex'] = df['sex'].replace("거",3)

'''
df['weather'] = df['weather'].replace("강풍",1)
df['weather'] = df['weather'].replace("눈",2)
df['weather'] = df['weather'].replace("맑음",3)
df['weather'] = df['weather'].replace("비",4)
df['weather'] = df['weather'].replace("안개",5)
df['weather'] = df['weather'].replace("흐림",6)
'''

df['name'] = df['name'].replace("남아프리카공화국",1)
df['name'] = df['name'].replace("뉴질랜드",2)
df['name'] = df['name'].replace("러시아",3)
df['name'] = df['name'].replace("모로코",4)
df['name'] = df['name'].replace("미국",5)
df['name'] = df['name'].replace("브라질",6)
df['name'] = df['name'].replace("아르헨티나",7)
df['name'] = df['name'].replace("아일랜드",8)
df['name'] = df['name'].replace("영국",9)
df['name'] = df['name'].replace("우크라이나",10)
df['name'] = df['name'].replace("인도",11)
df['name'] = df['name'].replace("일본",12)
df['name'] = df['name'].replace("중국",13)
df['name'] = df['name'].replace("캐나다",14)
df['name'] = df['name'].replace("프랑스",15)
df['name'] = df['name'].replace("제",16)
df['name'] = df['name'].replace("한",16)
df['name'] = df['name'].replace("한국",16)
df['name'] = df['name'].replace("한국(포)",16)
df['name'] = df['name'].replace("호주",17)

#등급x,[-가,-하]x,[0-9]x,[a-z]x.OPEN10,오픈10,미승20,신마30,군40
df['rank']=df['rank'].str.replace(pat="래",repl="1")
df['rank']=df['rank'].str.replace(pat="제",repl="2")
df['rank']=df['rank'].str.replace(pat="한",repl="3")
df['rank']=df['rank'].str.replace(pat="국",repl="4")
df['rank']=df['rank'].str.replace(pat="외",repl="5")
df['rank']=df['rank'].str.replace(pat="혼",repl="6")
df['rank']=df['rank'].str.replace(pat="C(.?)(.*)",repl=r"7\1")
df['rank']=df['rank'].str.replace(pat="OPEN",repl="10")
df['rank']=df['rank'].str.replace(pat="오픈",repl="10")
df['rank']=df['rank'].str.replace(pat="미승",repl="20")
df['rank']=df['rank'].str.replace(pat="신마",repl="30")
df['rank']=df['rank'].str.replace(pat="군",repl="40")
#등급,가,나,0,a,b,c,A,B,C
df['rank']=df['rank'].str.replace(pat="[-가-하]|[a-zA-Z등급]",repl="")

pd.set_option('display.max_columns',20)
df.columns
df.info()
df.describe()
df['jkno']=df['jkno'].astype(str)
df['jkno'] = df['jkno'].str.zfill(6)

df1 = pd.read_csv('d:/data/final/jockey.csv')
del df1['Unnamed: 0']
df1.columns
df1.info()
df1.describe()
df1['jkno'] = df1['jkno'].str.zfill(6)

df = pd.merge(df,df1,how='left',on='jkno')

winningp = df['jconsecutivewinningp'].mean()
df['jconsecutivewinningp'] = df['jconsecutivewinningp'].fillna(winningp)

del df['jkname']
del df['jkno']
#del df['track']

df.to_csv('d:/data/final/real/horse_total_merged.csv',encoding='utf-8-sig')