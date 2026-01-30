# ai-incident-analyzer
Build a production-style AI system that ingests logs and metrics, detects anomalies, and uses an LLM to summarize incidents and suggest likely root causes, with observability and reliability in mind.

<img width="390" height="687" alt="image" src="https://github.com/user-attachments/assets/30b2c3c3-d2b3-42a0-ab4c-39ef734713cc" />


## Sequence Diagram

<img width="600" height="687" alt="image" src="https://www.plantuml.com/plantuml/svg/TPF1Rjiy48JlV8f53Z_o0pLorw48Opk62ZiHmlIM2qjkQ08IgPAhk6xHT-zAaPGeG4u6EEFPsM-XwqmwKTVKWZpL26Kmtc8Ws22X8Hy3w611bl0G8y5QTnb11bsViy-m0eK_Emm6uRQE9o5qHp5qpGzCekLSRtohEUnY7nrKAThEOBe0dM5VBGq7fEJDqdFuu2c3mqmWgtAGIxcKPOWDzngRnzSCkLcQvlsaSmcTddsxfK_X2oPFPr0cfh6FKfzaAMBFODdSVEXKli5Ic5wyUVN9yWEV_Q7V_SNRSHO_V3BiOQiUPmRykxIjQz-Rhk0-h8okQzW6vmEEgMiXzXMilZAx0XG62tLqMHoMRyrmYon4AVjtkIdWBYPcwtyZ_0VFCPrqic0rQI7aPl3i2fZusu4_MY5tlQHKCSD6oDun3yvMYaNcLK25YV4G-6d3o_jgU_U_424IGlBkI12VU_ep0vEGmooSF1rZH-AaFVdW-cgC5XBcDeQCej-cJnd7iLR08oSw77nwEisbpkTWUAGifrQtIERurid4GFYBHWDVfu0T1ap3JbtJw7H-2fh7fHZPFNmcntCRwOWPi-ZlkneBo7Bbnh_QkSBxqTzRozey-obSZXE-l0PdBheUrgauRhTzXAl9cw_-UFjNiF397WdRc4XiRc0r1SnKXuFoqo2jtmdVrFtT6zHhfjerzJy0">



# AI Incident Analyzer with SRE AI Agent

A production-inspired **AI Agent system** that assists on-call engineers by analyzing incidents, deciding next actions, and escalating to humans when required.

This project goes beyond simple LLM integration and demonstrates how to build a **goal-driven AI agent** where the LLM is treated as a tool, not the decision-maker.

---

## 🚨 What Makes This an AI Agent (Not Just an LLM Call)

The system implements a **Virtual SRE Agent** with:

- Goal-oriented behavior  
- Explicit decision-making  
- Multiple possible actions  
- Stateful memory  
- Human feedback loops  

The agent decides:
- **Whether** to analyze an incident  
- **How deeply** to analyze  
- **Which prompt version** to use  
- **When to escalate to humans**  

---

## 🧠 Agent Goal

> Assist Site Reliability Engineers by summarizing incidents, suggesting likely causes, and escalating when AI confidence or trust is low.

---

## 🏗️ High-Level Flow

```
Incident Created
      ↓
SRE AI Agent
      ↓
Decision (Action Selection)
      ↓
┌──────────────────────────────┐
│ SKIP_ANALYSIS                │
│ BASIC_SUMMARY (LLM)          │
│ DEEP_ANALYSIS (LLM)          │
│ REQUEST_HUMAN_REVIEW         │
└──────────────────────────────┘
      ↓
Incident Updated
```

The **LLM is invoked only if the agent decides to do so**.

---

## 🤖 Agent Actions

| Action | Description |
|------|------------|
| SKIP_ANALYSIS | Low severity incident, AI not required |
| BASIC_SUMMARY | Lightweight AI summary |
| DEEP_ANALYSIS | More detailed AI reasoning |
| REQUEST_HUMAN_REVIEW | Escalation due to low trust or repeated failures |

---

## 🧠 Agent Memory (Stateful Behavior)

The agent maintains memory of **bad human feedback per service**.

- Repeated bad feedback alters future decisions  
- The agent escalates earlier  
- Unreliable AI paths are avoided  

This enables learning **without retraining models**.

---

## 🤖 LLM as a Tool

- LLMs are abstracted as tools  
- The agent controls when and how they are invoked  
- Failures never impact core system reliability  

---

## 👩‍💻 Human-in-the-Loop Feedback

Humans can submit feedback that directly influences agent behavior.

- Feedback is stored
- Future decisions adapt
- Human judgment always overrides AI

---

## 📦 Project Structure

```
src/main/java/com/example/agentai
│
├── AgentAiApplication.java
├── Incident.java
├── AgentAction.java
├── AgentMemory.java
├── PromptRegistry.java
├── LLMTool.java
├── IncidentStore.java
├── SREAgent.java
└── IncidentController.java
```

---

## 🚀 Running the Project

```bash
mvn spring-boot:run
```

### Create an incident
```bash
curl -X POST "http://localhost:8080/incident?service=orders&severity=HIGH"
```

### Fetch incident
```bash
curl http://localhost:8080/incident/1
```

### Submit bad feedback
```bash
curl -X POST "http://localhost:8080/feedback/bad?service=orders"
```

---

## 🧪 Why This Design Is Production-Ready

- AI is best-effort and non-blocking  
- Explicit decision boundaries  
- Safe failure modes  
- Human-in-the-loop governance  

---

## 🛣️ Future Enhancements

- Real OpenAI / local LLM integration  
- Agent SLOs and metrics  
- Multi-agent collaboration  
- Cost-aware planning  
- Prompt A/B testing  

---

## 📄 License

MIT
