import pandas as pd
import numpy as np

df = pd.read_csv('d:/data/final/real/horse_total_merged.csv')
del df['Unnamed: 0']
df['wghr1']=0
df['wghr2']=0
seouldf = pd.DataFrame(df[df.meet=="서울"])
jejudf = pd.DataFrame(df[df.meet=="제주"])
busandf = pd.DataFrame(df[df.meet=="부산경남"])
#seouldf.to_csv('d:/data/final/real/horse_total_merged_seoul.csv',encoding='utf-8-sig')
#jejudf.to_csv('d:/data/final/real/horse_total_merged_jeju.csv',encoding='utf-8-sig')
#busandf.to_csv('d:/data/final/real/horse_total_merged_busan.csv',encoding='utf-8-sig')

#temp is realrace df[all]
#temp2 is lastrace df[['hrname','rctime']]
tempseoul = pd.read_csv('d:/data/final/horse_total_merged_seoul.csv')
tempseoul = tempseoul[(tempseoul['ord'] > 0) & (tempseoul['ord'] < 17)]
seoul = seouldf[['rcdate','rcno','rcdist','rank']].groupby([seouldf['rcdate'],seouldf['rcno'],seouldf['rcdist'],seouldf['rank']])
pieces = dict(list(seoul))
for rcdate,rcno,rcdist,rank in pieces:
    temp = seouldf[(seouldf['rcdate']==rcdate) & (seouldf['rcno']==rcno) & (seouldf['rcdist']==rcdist) & (seouldf['rank']==rank)]
    del temp['meet']
    del temp['rcdate']
    del temp['rcno']
    del temp['rcdist']
    del temp['rank']

    temp2 = tempseoul[(tempseoul.rcdist==rcdist) & (tempseoul['rank']==rank)]
    temp2 = temp2[['hrname','rctime']]
    avg = temp2['rctime'].mean()

    temp2 = temp2.groupby(temp2['hrname'])
    pieces2 = dict(list(temp2))
    dic = dict()

    for k in pieces2.keys():
        dic[k] = pieces2[k]['rctime'].mean()
    dic
    lis=[]
    for i in temp['hrname']:
        if i in dic:
            lis.append((avg-dic[i])*10)
        else:
            lis.append(0)
    temp['rctime']=lis
    temp

    del temp['hrname']
    del temp['hrno']
    temp=temp[['wgbudam','chulno','name','age','sex','rating','sttime','rctime','wghr1','wghr2','jconsecutivewinningp']]
    temp.to_csv('d:/data/final/real/seoul/seoul_'+str(rcdate)+'_'+str(rcno)+'_'+str(rcdist)+'_'+str(rank)+'.csv',encoding='utf-8-sig')

tempjeju = pd.read_csv('d:/data/final/horse_total_merged_jeju.csv')
tempjeju = tempjeju[(tempjeju['ord'] > 0) & (tempjeju['ord'] < 17)]
jeju = jejudf[['rcdate','rcno','rcdist','rank']].groupby([jejudf['rcdate'],jejudf['rcno'],jejudf['rcdist'],jejudf['rank']])
pieces = dict(list(jeju))
for rcdate,rcno,rcdist,rank in pieces:
    temp = jejudf[(jejudf['rcdate']==rcdate) & (jejudf['rcno']==rcno) & (jejudf['rcdist']==rcdist) & (jejudf['rank']==rank)]
    del temp['meet']
    del temp['rcdate']
    del temp['rcno']
    del temp['rcdist']
    del temp['rank']

    temp2 = tempjeju[(tempjeju.rcdist==rcdist) & (tempjeju['rank']==rank)]
    temp2 = temp2[['hrname','rctime']]
    avg = temp2['rctime'].mean()

    temp2 = temp2.groupby(temp2['hrname'])
    pieces2 = dict(list(temp2))
    dic = dict()

    for k in pieces2.keys():
        dic[k] = pieces2[k]['rctime'].mean()
    dic
    lis=[]
    for i in temp['hrname']:
        if i in dic:
            lis.append((avg-dic[i])*10)
        else:
            lis.append(0)
    temp['rctime']=lis

    del temp['hrname']
    del temp['hrno']
    temp=temp[['wgbudam','chulno','name','age','sex','rating','sttime','rctime','wghr1','wghr2','jconsecutivewinningp']]
    temp.to_csv('d:/data/final/real/jeju/jeju_'+str(rcdate)+'_'+str(rcno)+'_'+str(rcdist)+'_'+str(rank)+'.csv',encoding='utf-8-sig')

tempbusan = pd.read_csv('d:/data/final/horse_total_merged_busan.csv')
tempbusan = tempbusan[(tempbusan['ord'] > 0) & (tempbusan['ord'] < 17)]
busan = busandf[['rcdate','rcno','rcdist','rank']].groupby([busandf['rcdate'],busandf['rcno'],busandf['rcdist'],busandf['rank']])
pieces = dict(list(busan))
for rcdate,rcno,rcdist,rank in pieces:
    temp = busandf[(busandf['rcdate']==rcdate) & (busandf['rcno']==rcno) & (busandf['rcdist']==rcdist) & (busandf['rank']==rank)]
    del temp['meet']
    del temp['rcdate']
    del temp['rcno']
    del temp['rcdist']
    del temp['rank']

    temp2 = tempbusan[(tempbusan.rcdist==rcdist) & (tempbusan['rank']==rank)]
    temp2 = temp2[['hrname','rctime']]
    avg = temp2['rctime'].mean()

    temp2 = temp2.groupby(temp2['hrname'])
    pieces2 = dict(list(temp2))
    dic = dict()

    for k in pieces2.keys():
        dic[k] = pieces2[k]['rctime'].mean()
    dic
    lis=[]
    for i in temp['hrname']:
        if i in dic:
            lis.append((avg-dic[i])*10)
        else:
            lis.append(0)
    temp['rctime']=lis

    del temp['hrname']
    del temp['hrno']
    temp=temp[['wgbudam','chulno','name','age','sex','rating','sttime','rctime','wghr1','wghr2','jconsecutivewinningp']]
    temp.to_csv('d:/data/final/real/busan/busan_'+str(rcdate)+'_'+str(rcno)+'_'+str(rcdist)+'_'+str(rank)+'.csv',encoding='utf-8-sig')