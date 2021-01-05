# data analysis and wrangling
import pandas as pd
import numpy as np
import random as rnd

# visualization
import seaborn as sns
import matplotlib.pyplot as plt

# machine learning
from sklearn.linear_model import LogisticRegression
from sklearn.svm import SVC, LinearSVC
from sklearn.ensemble import RandomForestClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.linear_model import Perceptron
from sklearn.linear_model import SGDClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split

#df = pd.read_csv('d:/data/rank1_1400_seoul.csv')
df = pd.read_csv('d:/data/rank5_1300_seoul.csv')
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
del df['hrname']
del df['hrno']
#del df['name']
del df['jkname']
del df['jkno']
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

#날씨
data_set['weather'] = data_set['weather'].replace("강풍",1)
data_set['weather'] = data_set['weather'].replace("눈",2)
data_set['weather'] = data_set['weather'].replace("맑음",3)
data_set['weather'] = data_set['weather'].replace("비",4)
data_set['weather'] = data_set['weather'].replace("안개",5)
data_set['weather'] = data_set['weather'].replace("흐림",6)

#등수
df = df[(df['ord'] > 0) & (df['ord'] < 17)]
df['lab'] = df['ord'].apply(lambda x: 0 if x<4 else 1)
del df['ord']

#국가
data_set['name'] = data_set['name'].replace("남아프리카공화국",1)
data_set['name'] = data_set['name'].replace("뉴질랜드",2)
data_set['name'] = data_set['name'].replace("러시아",3)
data_set['name'] = data_set['name'].replace("모로코",4)
data_set['name'] = data_set['name'].replace("미국",5)
data_set['name'] = data_set['name'].replace("브라질",6)
data_set['name'] = data_set['name'].replace("아르헨티나",7)
data_set['name'] = data_set['name'].replace("아일랜드",8)
data_set['name'] = data_set['name'].replace("영국",9)
data_set['name'] = data_set['name'].replace("우크라이나",10)
data_set['name'] = data_set['name'].replace("인도",11)
data_set['name'] = data_set['name'].replace("일본",12)
data_set['name'] = data_set['name'].replace("중국",13)
data_set['name'] = data_set['name'].replace("캐나다",14)
data_set['name'] = data_set['name'].replace("프랑스",15)
data_set['name'] = data_set['name'].replace("한국",16)
data_set['name'] = data_set['name'].replace("호주",17)

#성별
df['sex'] = df['sex'].replace("암",1)
df['sex'] = df['sex'].replace("수",2)
df['sex'] = df['sex'].replace("거",3)

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

#시간
df['rctime'] = df['rctime']*10.0-800.0

df.info()
df.describe()

#-----------------------------------------정수화--------------------------------------------------
df = df.astype('int')

#df.to_csv('d:/data/rank1_1400_seoul_1st.csv',encoding='utf-8-sig')

#-----------------------------------------시각화--------------------------------------------------
df[['wgbudam','lab']].groupby(['wgbudam'],as_index=False).mean().sort_values(by='wgbudam',ascending=False)
df[['name','lab']].groupby(['name'],as_index=False).mean().sort_values(by='name',ascending=False)
df[['age','lab']].groupby(['age'],as_index=False).mean().sort_values(by='age',ascending=False)
df[['sex','lab']].groupby(['sex'],as_index=False).mean().sort_values(by='sex',ascending=False)
df[['rating','lab']].groupby(['rating'],as_index=False).mean().sort_values(by='rating',ascending=False)
df[['wghr1','lab']].groupby(['wghr1'],as_index=False).mean().sort_values(by='wghr1',ascending=False)
df[['wghr2','lab']].groupby(['wghr2'],as_index=False).mean().sort_values(by='wghr2',ascending=False)

#-------------------------------------------------------------------학습-------------------------------------------------------------------------
#-----------------------------------------데이터셋 분리--------------------------------------------------
train_df, test_df = train_test_split(df, test_size=0.2, random_state=42, shuffle=True)
combine = [train_df, test_df]

Y_train = train_df["lab"]
X_train = train_df.drop("lab",axis=1)
Y_test = test_df["lab"]
X_test = test_df.drop("lab",axis=1)
X_train.shape, Y_train.shape, X_test.shape, Y_test.shape
#X_real = pd.DataFrame(data={'rank':[45,45,45,45,45,45,45,45,45,45,45,45,45,45],'rating':[28,30,30,34,34,29,31,30,26,24,28,30,27,32]})
#Y_real = pd.Series([1,1,1,2,2,2,2,2,2,2,2,2,2,2])
#X_real.shape,Y_real.shape
#-----------------------------------------------------------------------------------------------------
# �����ɸ� 1
# Support Vector Machines
svc = SVC()
svc.fit(X_train, Y_train)
Y_pred = svc.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_svc = round(svc.score(X_test, Y_test) * 100, 2)
acc_svc

#-----------------------------------------------------------------------------------------------------

#Decision Tree
#X_train.describe()
decision_tree = DecisionTreeClassifier()
decision_tree.fit(X_train, Y_train)
Y_pred = decision_tree.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_decision_tree = round(decision_tree.score(X_test, Y_test) * 100, 2)
acc_decision_tree

#-------------------------------------------------------------------------------------------------------

# Logistic Regression
logreg = LogisticRegression()
logreg.fit(X_train, Y_train)
Y_pred = logreg.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_log = round(logreg.score(X_test, Y_test) * 100, 2)
acc_log

#-------------------------------------------------------------------------------------------------------

#KNN
a=[]
b=[]
for i in range(1,100):
    knn = KNeighborsClassifier(n_neighbors = i,weights='uniform')
    knn.fit(X_train, Y_train)
    #Y_pred = knn.predict(X_test)
    #Y_pred.tolist()
    #Y_test.tolist()
    acc_knn = round(knn.score(X_test, Y_test) * 100, 2)
    a.append(acc_knn)
for i in range(1,100):
    knn = KNeighborsClassifier(n_neighbors = i,weights='distance')
    knn.fit(X_train, Y_train)
    #Y_pred = knn.predict(X_test)
    #Y_pred.tolist()
    #Y_test.tolist()
    acc_knn = round(knn.score(X_test, Y_test) * 100, 2)
    b.append(acc_knn)

knn = KNeighborsClassifier(n_neighbors = 5,weights='uniform')
knn.fit(X_train, Y_train)
Y_pred = knn.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_knn = round(knn.score(X_test, Y_test) * 100, 2)
acc_knn
knn = KNeighborsClassifier(n_neighbors = 19,weights='distance')
knn.fit(X_train, Y_train)
Y_pred = knn.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_knn = round(knn.score(X_test, Y_test) * 100, 2)
acc_knn
#-------------------------------------------------------------------------------------------------------

# Perceptron
perceptron = Perceptron()
perceptron.fit(X_train, Y_train)
Y_pred = perceptron.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_perceptron = round(perceptron.score(X_test, Y_test) * 100, 2)
acc_perceptron

#-------------------------------------------------------------------------------------------------------
#�����ɸ� 2
# Linear SVC
linear_svc = LinearSVC()
linear_svc.fit(X_train, Y_train)
Y_pred = linear_svc.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_linear_svc = round(linear_svc.score(X_test, Y_test) * 100, 2)
acc_linear_svc

#-------------------------------------------------------------------------------------------------------

# Stochastic Gradient Descent
sgd = SGDClassifier()
sgd.fit(X_train, Y_train)
Y_pred = sgd.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_sgd = round(sgd.score(X_test, Y_test) * 100, 2)
acc_sgd

#-------------------------------------------------------------------------------------------------------

# Random Forest
random_forest = RandomForestClassifier(n_estimators=100)
random_forest.fit(X_train, Y_train)
Y_pred = random_forest.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_random_forest = round(random_forest.score(X_test, Y_test) * 100, 2)
acc_random_forest

#----------------------------------------------------------------------------------------------------

# Gaussian Naive Bayes

gaussian = GaussianNB()
gaussian.fit(X_train, Y_train)
Y_pred = gaussian.predict(X_test)
Y_pred.tolist()
Y_test.tolist()
acc_gaussian = round(gaussian.score(X_test, Y_test) * 100, 2)
acc_gaussian

#----------------------------------------------------------------------------------------------------
models = pd.DataFrame({
    'Model': ['Support Vector Machines', 'KNN', 'Logistic Regression', 
              'Random Forest', 'Naive Bayes', 'Perceptron', 
              'Stochastic Gradient Decent', 'Linear SVC', 
              'Decision Tree'],
    'Score': [acc_svc, acc_knn, acc_log, 
              acc_random_forest, acc_gaussian, acc_perceptron, 
              acc_sgd, acc_linear_svc, acc_decision_tree]})
models.sort_values(by='Score', ascending=False)