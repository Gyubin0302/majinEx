import pathlib

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers

print(tf.__version__)

df = pd.read_csv('d:/data/rank1_1400_seoul.csv')
#df = pd.read_csv('d:/data/rank5_1300_seoul.csv')
del df['Unnamed: 0']
del df['meet']
del df['rcdate']
del df['rcday']
del df['rcno']
del df['rcdist']
del df['rank']
#del df['weather']
#del df['track']
#del df['chulno']
#del df['hrname']
del df['hrno']
#del df['name']
del df['jkname']
#del df['jkno']
del df['trname']
del df['trno']
#del df['rctime']
del df['diffunit']
del df['winodds']
del df['plcodds']
del df['track']

df.columns
pd.set_option('display.max_columns',20)
df.info()
df.describe()

#무게
df[['wghr1','wghr2']] = df['wghr'].str.split('(',n=1,expand=True)
df['wghr2'] = df['wghr2'].replace(")","0)")
df['wghr2']=df['wghr2'].str.replace(pat=")",repl="")
df['wghr2']=df['wghr2'].str.replace(pat="+",repl="")
df = df.dropna()
df['wghr1'] = df['wghr1'].astype('int')
df['wghr2'] = df['wghr2'].astype('int')
df = df[(df.wghr1 > 160) & (df.wghr1 < 650)]
df = df[(df.wghr2 < 100) & (df.wghr2 > -100)]
del df['wghr']

#등수 탈락제거
df = df[(df['ord'] > 0) & (df['ord'] < 17)]

#상관성
#sns.pairplot(df[["wgbudam", "ord", "chulno", "age", "rating", "rctime", "wghr1", "wghr2"]], diag_kind="kde")
#plt.show()

#기록 정규화
#df1 = pd.DataFrame({'rctime1':(df['rctime'].mean()-df['rctime'].groupby(df['hrname']).mean())*10})
#del df['hrname']
df
#df1.index
#df = pd.merge(df,df1,how='left',on='hrname')
#sns.pairplot(df[["wgbudam", "ord", "chulno", "age", "rating", "rctime", "wghr1", "wghr2", "rctime1"]], diag_kind="kde")
#plt.show()
#df.to_csv('d:/data/rank1_1400_seoul_2st.csv',encoding='utf-8-sig')

df
df3 = df[['hrname','rctime']]
df3 = df3.groupby(df['hrname'])
pieces = dict(list(df3))

for k in pieces.keys():
    for i in range (0,len(pieces[k])+1):
        pieces[k].iloc[-i,1] = pieces[k][-i:].mean()[0]

avg = df['rctime'].mean()
for k in pieces.keys():
    for i in range (0,len(pieces[k])):
        df.loc[pieces[k].index[i],('rctime')] = (avg-pieces[k].iloc[i,1])*10

df.to_csv('d:/data/rank1_1400_seoul_4st.csv',encoding='utf-8-sig')