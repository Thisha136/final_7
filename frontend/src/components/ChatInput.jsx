import "../styles/ChatInput.css";

import { FaPaperPlane } from "react-icons/fa";

export default function ChatInput(){

return(

<div className="inputArea">

<input

placeholder="Ask a question..."

/>

<button>

<FaPaperPlane/>

</button>

</div>

);

}