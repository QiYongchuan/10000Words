import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [word, setWord] = useState('');
  const [definition, setDefinition] = useState('');
  const [summary, setSummary] = useState('');
  const [example, setExample] = useState('');
  const [imageSrc, setImage] = useState('');
  const [results,setRes] = useState('');  

  const handleWordChange = (event) => {
    setWord(event.target.value);
  };

  const handleSubmit = async () => {
    // 这里您需要添加代码来调用后端API获取单词解释和图片
    try{
      // const response = await axios.post('http://localhost:5000/get-word-info', { word: word });  # 原来flask框架的
      const response = await axios.post('http://localhost:8080/chat', { word: word });
    
       // 尝试解析content属性
     const parsedContent = JSON.parse(response.data.content);
     const { concept, definition, examples, summary } = parsedContent;   
     console.log(parsedContent,"解析后的json");  // 解析成功了！

      console.log(response.data, "response data" ,definition);
      setDefinition(definition);
      setSummary(summary);
      setExample(examples); // 注意这里使用复数形式

      console.log(concept,"concept");

      //  showRes(); // 假设你想要立即显示一个单词列表，你可以在这里调用showRes
    } catch(error){
      console.error('Error fetching word info 没拿到单词信息',error.response || error)
      // console.log("错误码：",error.response.status);
      if (error.response && error.response.status === 401) {
        // 如果是401错误，则重定向到登录页面
        window.location.href = '/login'; // 前端应用中的登录页面路由
      }
    }
    
  };

  const showRes = async()=>{
      axios.get('http://localhost:8080/words')
  .then(response => {
    // 处理数据
    console.log(response.data);
    setRes(response.data)
   
  })
  .catch(error => {
    console.error('Error fetching words', error);
  });
  }

  const showImg = async()=>{
    axios.post('http://localhost:8080/words/img', { word: word })
    .then(response=>{
      console.log(response.data);
      const base64Image = response.data;
      setImage(`data:image/png;base64,${base64Image}`);
    })
  }

  return (
    <div className="App">
      <header className="App-header">
        <input type="text" value={word} onChange={handleWordChange} placeholder="输入单词" />
        <button onClick={handleSubmit}>提交</button>
        <button onClick={showRes}>成果</button>
        <button onClick={showImg}>图片</button>
        {imageSrc && <img src={imageSrc} alt="Generated from word" />}
        {definition && <p>definition:{definition}</p>}
        {summary && <p>summary:{summary}</p>}
        {example  && example.length>0 && (
          <ul>
            examples:
            {
              example.map((item)=>(
                <li>{item}</li>
              ))
            }
          </ul>
        )}
        {/* {image && <img src={image} alt="Word depiction" />} */}
        
        {results && results.length > 0 && (
        <ul>
          {results.map((item) => (
            <li key={item.id}>
             {item.id} Word: {item.word}, 

            </li>
          ))}
        </ul>
      )}
      </header>
    </div>
  );
}

export default App;
