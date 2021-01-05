import pandas as pd

df = pd.read_csv('d:/data/final/horse_total_merged.csv')
del df['Unnamed: 0']
seouldf = pd.DataFrame(df[df.meet=="서울"])
jejudf = pd.DataFrame(df[df.meet=="제주"])
busandf = pd.DataFrame(df[df.meet=="부산경남"])

seouldf.to_csv('d:/data/final/horse_total_merged_seoul.csv',encoding='utf-8-sig')
jejudf.to_csv('d:/data/final/horse_total_merged_jeju.csv',encoding='utf-8-sig')
busandf.to_csv('d:/data/final/horse_total_merged_busan.csv',encoding='utf-8-sig')

seoul = seouldf[['rcdist','rank']].groupby([seouldf['rcdist'],seouldf['rank']])
pieces = dict(list(seoul))
for distance,rank in pieces:
    temp = seouldf[(seouldf['rcdist']==distance) & (seouldf['rank']==rank)]
    del temp['meet']
    del temp['rcdist']
    del temp['rank']

    temp2 = temp[['hrname','rctime']]
    temp2 = temp2.groupby(temp['hrname'])
    pieces2 = dict(list(temp2))
    for k in pieces2.keys():
        for i in range (0,len(pieces2[k])+1):
            pieces2[k].iloc[-i,1] = pieces2[k][-i:].mean()[0]

    avg = temp['rctime'].mean()
    for k in pieces2.keys():
        for i in range (0,len(pieces2[k])):
            temp.loc[pieces2[k].index[i],('rctime')] = (avg-pieces2[k].iloc[i,1])*10
            
    del temp['hrname']
    del temp['hrno']

    temp.to_csv('d:/data/final/seoul/seoul_'+str(distance)+'_'+str(rank)+'.csv',encoding='utf-8-sig')

jeju = jejudf[['rcdist','rank']].groupby([jejudf['rcdist'],jejudf['rank']])
pieces = dict(list(jeju))
for distance,rank in pieces:
    temp = jejudf[(jejudf['rcdist']==distance) & (jejudf['rank']==rank)]
    del temp['meet']
    del temp['rcdist']
    del temp['rank']

    temp2 = temp[['hrname','rctime']]
    temp2 = temp2.groupby(temp['hrname'])
    pieces2 = dict(list(temp2))
    for k in pieces2.keys():
        for i in range (0,len(pieces2[k])+1):
            pieces2[k].iloc[-i,1] = pieces2[k][-i:].mean()[0]

    avg = temp['rctime'].mean()
    for k in pieces2.keys():
        for i in range (0,len(pieces2[k])):
            temp.loc[pieces2[k].index[i],('rctime')] = (avg-pieces2[k].iloc[i,1])*10
    
    del temp['hrname']
    del temp['hrno']

    temp.to_csv('d:/data/final/jeju/jeju_'+str(distance)+'_'+str(rank)+'.csv',encoding='utf-8-sig')

busan = busandf[['rcdist','rank']].groupby([busandf['rcdist'],busandf['rank']])
pieces = dict(list(busan))
for distance,rank in pieces:
    temp = busandf[(busandf['rcdist']==distance) & (busandf['rank']==rank)]
    del temp['meet']
    del temp['rcdist']
    del temp['rank']

    temp2 = temp[['hrname','rctime']]
    temp2 = temp2.groupby(temp['hrname'])
    pieces2 = dict(list(temp2))
    for k in pieces2.keys():
        for i in range (0,len(pieces2[k])+1):
            pieces2[k].iloc[-i,1] = pieces2[k][-i:].mean()[0]

    avg = temp['rctime'].mean()
    for k in pieces2.keys():
        for i in range (0,len(pieces2[k])):
            temp.loc[pieces2[k].index[i],('rctime')] = (avg-pieces2[k].iloc[i,1])*10

    del temp['hrname']
    del temp['hrno']

    temp.to_csv('d:/data/final/busan/busan_'+str(distance)+'_'+str(rank)+'.csv',encoding='utf-8-sig')